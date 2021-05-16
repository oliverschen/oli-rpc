package com.github.oliverschen.olirpc.remote.http;


import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.remote.OliRpcRemote;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import com.github.oliverschen.olirpc.util.JsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

import static com.github.oliverschen.olirpc.util.JsonUtil.MEDIA_TYPE;

/**
 * httpClient 请求远端
 * @author ck
 */
public class OkHttpRemote implements OliRpcRemote {

    private static final Logger log = LoggerFactory.getLogger(OkHttpRemote.class);
    private final String url;

    /**
     * 初始化
     */
    public static OkHttpRemote init(final String url) {
        return new OkHttpRemote(url);
    }

    public OkHttpRemote(String url) {
        this.url = url;
    }

    /**
     * send http rpc by okHttp
     * @param oliReq oliReq
     * @return OliResp
     */
    private OliResp post(OliReq oliReq) {
        try {
            String json = JsonUtil.MAPPER.writeValueAsString(oliReq);
            log.debug("request params is {}", json);
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(MEDIA_TYPE, json))
                    .build();
            String response = client.newCall(request).execute().body().string();
            log.info("response is {}", response);
            return JsonUtil.MAPPER.readValue(response, OliResp.class);
        } catch (IOException e) {
            log.error("okHttp request error:", e);
            throw new OliException("request error");
        }
    }


    @Override
    public OliResp send(OliReq req) {
        return post(req);
    }
}
