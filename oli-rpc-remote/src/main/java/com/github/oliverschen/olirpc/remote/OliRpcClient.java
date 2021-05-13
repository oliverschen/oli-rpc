package com.github.oliverschen.olirpc.remote;

import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;

/**
 * @author ck
 */
public interface OliRpcClient {

    /**
     * 发送数据到 server
     * @param req OliReq
     * @return OliRpcFuture
     */
    OliResp send(OliReq req);

}
