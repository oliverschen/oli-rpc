package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ck
 */
public class OliRpcFuture extends CompletableFuture<Object> {

    private Long id;
    private OliResp resp;
    private OliReq req;

    public OliRpcFuture(OliReq req) {
        this.id = req.getId();
        this.req = req;
    }

    private static final ConcurrentHashMap<Long, OliRpcFuture> FUTURE =
            new ConcurrentHashMap<>();

    public void put(Long id) {
        FUTURE.putIfAbsent(id, this);
    }


    public static OliRpcFuture obtain(Long id) {
        OliRpcFuture future = FUTURE.get(id);
        if (future != null) {
            return future;
        } else {
            throw new OliException("oliRpcFuture not found");
        }
    }

    public void setResp(OliResp resp) {
        this.resp = resp;
    }

    public OliResp getResp() {
        return this.resp;
    }
}
