import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;

public class HttpResponseHandler extends SimpleChannelInboundHandler<HttpContent> {

    public HttpResponseHandler(boolean autoRelease) {
        super(autoRelease);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpContent msg) throws Exception {
        ByteBuf buf = msg.content();
        System.out.println("服务端返回结果:" + buf.toString(io.netty.util.CharsetUtil.UTF_8));
        buf.release();
        ctx.channel().close();
    }
}
