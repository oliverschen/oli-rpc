package com.github.oliverschen.olirpc.remote.netty.server;

import com.github.oliverschen.olirpc.invoker.OliInvoker;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ck
 */
public class ServerInboundHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(ServerInboundHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        OliResp resp = null;
        if (msg instanceof OliReq) {
            OliReq req = (OliReq) msg;
            log.debug("request data :{}", req.toString());
            resp = OliInvoker.invoke(req);
            log.info("invoke result:{}", resp.toString());
        }
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("netty service process error :", cause);
        super.exceptionCaught(ctx, cause);
    }
}
