package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.remote.codec.*;
import com.github.oliverschen.olirpc.remote.OliRpcClient;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import com.github.oliverschen.olirpc.serialization.json.JsonSerialization;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

import static com.github.oliverschen.olirpc.remote.netty.client.OliRpcFuture.FUTURE;


/**
 * netty client
 *
 * @author ck
 */
public class OliNetty implements OliRpcClient {

    private static final Logger log = LoggerFactory.getLogger(OliNetty.class);
    private Channel channel;
    private EventLoopGroup group;
    private final String host;
    private final Integer port;

    public static OliNetty init(String host, Integer port) {
        return new OliNetty(host, port);
    }

    public OliNetty(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public OliNetty connect() {
        this.group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyDecoder(OliResp.class, new JsonSerialization()));
                        pipeline.addLast(new NettyEncoder(OliReq.class, new JsonSerialization()));
                        pipeline.addLast(new NettyClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            channel = channelFuture.channel();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("oli-rpc netty client bind error:", e);
        }
        log.info("netty client connect success");
        return this;
    }

    @Override
    public OliResp send(OliReq req) {
        FUTURE.put(req.getId(),new OliRpcFuture());
        channel.writeAndFlush(req);
        OliRpcFuture result = FUTURE.get(req.getId());
        return result.getResp();
    }


    @PreDestroy
    public void close() {
        this.group.shutdownGracefully();
    }

}
