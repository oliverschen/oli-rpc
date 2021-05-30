package com.github.oliverschen.olirpc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;

/**
 * @author ck
 */
public final class JsonUtil {

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");


    static {
        // pojo 属性字段缺失，忽略 json 中多余的字段
        MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置关闭序列化空对象抛异常
        MAPPER.configure(FAIL_ON_EMPTY_BEANS, false);
        // 识别 json 字符串中的 '\' 字符
        MAPPER.configure(ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
    }
}
