package io.kimmking.rpcfx.client;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;

public class NettyHttpClient {

    private ChannelFuture channelFuture;

    private String host;

    private int port;

    private AtomicReference<CompletableFuture<String>> reference = new AtomicReference<>();

    public synchronized void connect(String host, int port) throws Exception {

        this.host = host;

        this.port = port;

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
                    ch.pipeline().addLast(new NettyHttpResponseHandler(reference));
                    ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
                }
            });

            // 发起连接
            System.out.println("Connected to [" + host + ':' + port + ']');
            channelFuture = b.connect(host, port).syncUninterruptibly();
            System.out.println("Connected finish...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public synchronized String post(String reqJson, String uri) throws Exception {
        System.out.println("Sending request(s)...");
        CompletableFuture<String> resultFuture = new CompletableFuture<>();
        reference.set(resultFuture);
        DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                uri, Unpooled.wrappedBuffer(reqJson.getBytes("UTF-8")));
        // 构建http请求
        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        // 发送http请求
        channelFuture.channel().writeAndFlush(request);
        System.out.println("Finished request(s)");
        return resultFuture.get();
    }

    public static void main(String[] args) throws Exception {
        NettyHttpClient httpClient = new NettyHttpClient();
        httpClient.connect("localhost", 8080);

    }
}
