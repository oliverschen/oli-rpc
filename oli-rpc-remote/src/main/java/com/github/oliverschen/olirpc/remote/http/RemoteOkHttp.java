package com.github.oliverschen.olirpc.remote.http;


import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import com.github.oliverschen.olirpc.util.JsonUtil;
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


    /**
     * send http rpc by okHttp
     * @param oliReq oliReq
     * @param url target url
     * @return OliResp
     * @throws IOException
     */
    public static OliResp post(OliReq oliReq, String url) throws IOException {
        String json = JsonUtil.MAPPER.writeValueAsString(oliReq);
        log.debug("request params is {}", json);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JsonUtil.MEDIA_TYPE, json))
                .build();
        String response = client.newCall(request).execute().body().string();
        log.debug("response is {}", response);
        return JsonUtil.MAPPER.readValue(response, OliResp.class);
    }


}
