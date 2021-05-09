package com.github.oliverschen.olirpc.remote.netty.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * netty client channel initializer handler
 * @author ck
 */
public class InitHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
        ch.pipeline().addLast(new HttpResponseDecoder());
        // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
        ch.pipeline().addLast(new HttpRequestEncoder());
        ch.pipeline().addLast(new NettyClientHandler());
    }
}
