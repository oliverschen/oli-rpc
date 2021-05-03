package com.github.oliverschen.olirpc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;

/**
 * @author ck
 */
public final class JsonUtil {

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
}
