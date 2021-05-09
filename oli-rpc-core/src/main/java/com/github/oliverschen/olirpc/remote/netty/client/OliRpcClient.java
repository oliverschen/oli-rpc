package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.request.OliReq;

/**
 * @author ck
 */
public interface OliRpcClient {

    /**
     * 发送数据到 server
     * @param req OliReq
     * @return OliRpcFuture
     */
    OliRpcFuture send(OliReq req);

}
