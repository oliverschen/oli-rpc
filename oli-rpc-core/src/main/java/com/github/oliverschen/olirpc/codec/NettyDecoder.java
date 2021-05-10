package com.github.oliverschen.olirpc.codec;

import com.github.oliverschen.olirpc.serialization.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author ck
 */
public class NettyDecoder extends ByteToMessageDecoder {

    private final Class<?> clz;
    private final Serialization serialization;

    public NettyDecoder(Class<?> clz, Serialization serialization){
        this.clz = clz;
        this.serialization = serialization;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if(byteBuf.readableBytes() < 4){
            return;
        }

        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);

        Object obj = serialization.deSerialize(data, clz);
        list.add(obj);
    }
}
