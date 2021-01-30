package io.github.kimmking.gateway.outbound.nettyclient;

import io.github.kimmking.gateway.filter.HeaderHttpRequestFilter;
import io.github.kimmking.gateway.filter.HeaderHttpResponseFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpResponseHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext serverCtx;

    private HttpResponseFilter httpResponseFilter = new HeaderHttpResponseFilter();

    public HttpResponseHandler(ChannelHandlerContext serverCtx) {
        this.serverCtx = serverCtx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof HttpResponse)
            {
                HttpResponse response = (HttpResponse) msg;
                System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
            }
            if(msg instanceof HttpContent)
            {
                HttpContent content = (HttpContent)msg;
                ByteBuf buf = content.content();
                handleResponse(serverCtx, buf);
                buf.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
            ctx.channel().close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.channel().close();
    }


    private void handleResponse(ChannelHandlerContext serverCtx, ByteBuf buf) throws Exception {
        FullHttpResponse response = null;
        try {
            byte[] body = buf.toString(io.netty.util.CharsetUtil.UTF_8).getBytes();

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", body.length);

            httpResponseFilter.filter(response);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(serverCtx, e);
        } finally {
            serverCtx.writeAndFlush(response);
        }

    }
}
