package com.github.oliverschen.olirpc.serialization;

/**
 * 序列化
 *
 * @author ck
 */
public interface Serialization {

    /**
     * 序列化
     * @param obj obj
     * @param <T> T
     * @return byte[]
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     * @param data byte[]
     * @param clz Class
     * @param <T> T
     * @return T
     */
    <T> T deSerialize(byte[] data,Class<T> clz);
}
