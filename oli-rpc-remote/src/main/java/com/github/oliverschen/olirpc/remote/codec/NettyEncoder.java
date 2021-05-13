package com.github.oliverschen.olirpc.remote.codec;

import com.github.oliverschen.olirpc.serialization.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * netty 解码器
 * @author ck
 */
public class NettyEncoder extends MessageToByteEncoder<Object> {

    private final Class<?> clz;
    private final Serialization serialization;

    public NettyEncoder(Class<?> clz, Serialization serialization){
        this.clz = clz;
        this.serialization = serialization;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) {
        if(clz != null){
            byte[] bytes = serialization.serialize(o);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }
}
