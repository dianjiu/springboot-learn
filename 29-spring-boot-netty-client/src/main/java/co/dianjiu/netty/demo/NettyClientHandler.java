package co.dianjiu.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 客户端处理类
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("NettyClientHandler Active");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks !", CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一个消息时被调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("--------");
        ByteBuf in = (ByteBuf) msg;
        System.out.println("NettyClientHandler read Message:"+in.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

