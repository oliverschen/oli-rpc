package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.response.OliResp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty client out bound handler
 *
 * @author ck
 */
public class InBoundHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(InBoundHandler.class);

    /**
     * client 连接 server 完成立即执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channel active complete：{}",ctx.channel());
        super.channelActive(ctx);
    }

    /**
     * client 接收 server 发送的数据
     *
     * @param ctx
     * @param msg 发送数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof OliResp) {
               // todo 写数据到 调用端
            }
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }

    /**
     * 发生异常时
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        log.error("oli-rpc client send data throw exception:", cause);
        ctx.close();
    }
}
