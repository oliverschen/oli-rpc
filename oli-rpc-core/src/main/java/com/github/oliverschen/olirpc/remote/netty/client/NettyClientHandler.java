package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.response.OliResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.oliverschen.olirpc.remote.netty.client.OliRpcFuture.FUTURE;

/**
 * netty client out bound handler
 *
 * @author ck
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<OliResp> {

    private static final Logger log = LoggerFactory.getLogger(NettyClientHandler.class);


    /**
     * 读服务端数据
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OliResp msg) {
        log.info("response info :{}", msg);
        OliRpcFuture future = FUTURE.get(msg.getId());
        future.setResp(msg);
        log.info("bind response to future complete");
    }

    /**
     * 发生异常时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        log.error("oli-rpc client send data throw exception:", cause);
        ctx.close();
    }
}
