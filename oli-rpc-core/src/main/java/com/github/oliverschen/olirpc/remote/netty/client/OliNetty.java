package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.request.OliReq;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

/**
 * netty client
 *
 * @author ck
 */
public class OliNetty implements OliRpcClient {

    private static final Logger log = LoggerFactory.getLogger(OliNetty.class);
    private Channel channel;
    private EventLoopGroup group;

    public OliNetty() {
    }

    public OliNetty(String host, Integer port) {
        this.group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(SocketChannel.class)
                .handler(new InitHandler());

        ChannelFuture future = bootstrap.connect(host, port);
        this.channel = future.channel();
        log.info("netty client connect success");
    }

    @Override
    public OliRpcFuture send(OliReq req) {
        OliRpcFuture result = new OliRpcFuture(req);
        result.put(req.getId());
        channel.writeAndFlush(req).addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                log.info("send data success");
            }else {
                f.channel().close();
                result.completeExceptionally(f.cause());
                log.error("send data error:", f.cause());
            }
        });
        return result;
    }


    @PreDestroy
    public void close() {
        this.group.shutdownGracefully();
    }

}
