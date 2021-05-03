package com.github.oliverschen.olirpc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;

/**
 * @author ck
 */
public class JsonUtil {

    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
}
