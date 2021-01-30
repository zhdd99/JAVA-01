package io.github.kimmking.gateway.outbound.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

public class HttpClient {

    public void connect(String host, int port, String uri) throws Exception{
        connect(host, port, uri, null);
    }

    public void connect(String host, int port, String uri, ChannelHandlerContext serverCtx) throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.remoteAddress(host, port);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpResponseHandler(serverCtx));
                }
            });

            // 发起连接
            System.out.println("Connected to [" + host + ':' + port + ']');
            ChannelFuture f = b.connect(host, port).syncUninterruptibly();
            System.out.println("Sending request(s)...");
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
                uri, Unpooled.EMPTY_BUFFER);
            // 构建http请求
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            // 发送http请求
            f.channel().writeAndFlush(request);
            System.out.println("Finished request(s)");

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        httpClient.connect("localhost", 8803,"");
    }
}
