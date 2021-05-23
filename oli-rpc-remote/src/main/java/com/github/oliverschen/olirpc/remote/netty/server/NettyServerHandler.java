package com.github.oliverschen.olirpc.remote.netty.server;

import com.github.oliverschen.olirpc.remote.invoker.OliInvoker;
import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ck
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<OliReq> {
    private static final Logger log = LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OliReq msg) throws Exception {
        OliResp resp;
        log.debug("request data :{}", msg);
        resp = OliInvoker.invoke(msg);
        log.info("invoke result:{}", resp);
        resp.setId(msg.getId());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("netty service process error :", cause);
        super.exceptionCaught(ctx, cause);
    }
}
