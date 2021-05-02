package com.github.oliverschen.olirpc.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * httpClient 请求远端
 * @author ck
 */
public class RemoteOkHttp {

    private static final Logger log = LoggerFactory.getLogger(RemoteOkHttp.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    public static OliResp post(OliReq oliReq, String url) throws IOException {
        String json = MAPPER.writeValueAsString(oliReq);
        log.debug("request params is {}", json);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE, json))
                .build();
        String response = client.newCall(request).execute().body().toString();
        log.debug("response is {}", response);
        return MAPPER.readValue(response, OliResp.class);
    }


}
