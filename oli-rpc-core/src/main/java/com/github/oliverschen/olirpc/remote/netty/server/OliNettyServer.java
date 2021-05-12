package com.github.oliverschen.olirpc.remote.netty.server;

import com.github.oliverschen.olirpc.codec.NettyDecoder;
import com.github.oliverschen.olirpc.codec.NettyEncoder;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import com.github.oliverschen.olirpc.serialization.JsonSerialization;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

import static com.github.oliverschen.olirpc.constant.Constants.NETTY_SERVER_DEFAULT_PORT;

/**
 * netty server
 *
 * @author ck
 */
public class OliNettyServer {

    private static final Logger log = LoggerFactory.getLogger(OliNettyServer.class);

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workGroup;

    public static OliNettyServer init() {
        return new OliNettyServer();
    }

    /**
     * start oli-rpc netty server in port NETTY_SERVER_DEFAULT_PORT:6789
     */
    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup(), workGroup())
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyEncoder(OliResp.class, new JsonSerialization()));
                        pipeline.addLast(new NettyDecoder(OliReq.class, new JsonSerialization()));
                        pipeline.addLast(new NettyServerHandler());
                    }
                });
        try {
            log.info("oli-rpc netty server starting");
            ChannelFuture channelFuture = bootstrap.bind(NETTY_SERVER_DEFAULT_PORT).sync();
            log.info("oli-rpc netty server started");
            // 阻塞，等待关闭事件才会关闭服务端
            channelFuture.channel().closeFuture().sync();
        } catch ( InterruptedException e) {
            log.error("oli-rpc netty server start error:", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * workerGroup
     */
    private EventLoopGroup workGroup() {
        // 默认 work 线程数量是 CPU 核心数 * 2
        workGroup = new NioEventLoopGroup();
        return workGroup;
    }

    /**
     * bossGroup
     */
    private EventLoopGroup bossGroup() {
        bossGroup = new NioEventLoopGroup(1);
        return bossGroup;
    }

    @PreDestroy
    public void close() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
        log.info("oli-rpc netty server was stopped");
    }


}
