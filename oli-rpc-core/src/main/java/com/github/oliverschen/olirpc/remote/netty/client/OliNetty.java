package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.request.OliReq;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty client
 *
 * @author ck
 */
public class OliNetty {

    private static final Logger log = LoggerFactory.getLogger(OliNetty.class);

    private String host;
    private Integer port;
    private OliReq oliReq;

    public OliNetty() {
    }

    public OliNetty(String host, Integer port, OliReq req) {
        this.host = host;
        this.port = port;
        this.oliReq = req;
    }

    /**
     * connect to netty server
     */
    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(SocketChannel.class)
                .handler(new InitHandler());

        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().write(oliReq);
            future.channel().flush();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("oli-rpc netty client error:", e);
            Thread.currentThread().interrupt();
        } finally {
            group.shutdownGracefully();
            log.debug("oli-rpc netty client is shutdown");
        }
    }


}
