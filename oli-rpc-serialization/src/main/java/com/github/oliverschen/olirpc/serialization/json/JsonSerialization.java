package com.github.oliverschen.olirpc.serialization.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.oliverschen.olirpc.serialization.Serialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * Json 序列化，使用 Jackson 框架
 * @author ck
 */
public class JsonSerialization implements Serialization {
    private static final Logger log = LoggerFactory.getLogger(JsonSerialization.class);

    @Override
    public <T> byte[] serialize(T obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("json serialization encode error:", e);
        }
        return new byte[]{};
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clz) {
        try {
            return MAPPER.readValue(data, clz);
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }
}
