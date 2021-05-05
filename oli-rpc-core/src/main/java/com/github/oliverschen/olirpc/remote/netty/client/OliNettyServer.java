package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.remote.netty.ServerInboundHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.github.oliverschen.olirpc.constant.Constants.NETTY_SERVER_DEFAULT_PORT;

/**
 * netty server
 *
 * @author ck
 */
public class OliNettyServer {

    private static final Logger log = LoggerFactory.getLogger(OliNettyServer.class);

    /**
     * start oli-rpc netty server in port NETTY_SERVER_DEFAULT_PORT:6789
     */
    public void start() {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 默认 work 线程数量是 CPU 核心数 * 2
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ServerInboundHandler());
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            Channel channel = bootstrap.bind(hostAddress, NETTY_SERVER_DEFAULT_PORT).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException | UnknownHostException e) {
            e.printStackTrace();
            log.error("oli-rpc netty server start error:", e);
            Thread.currentThread().interrupt();
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            log.debug("oli-rpc bossGroup and work Group shutdown");
        }
    }


}
