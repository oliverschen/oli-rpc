package com.github.oliverschen.olirpc.remote;

import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;

/**
 * @author ck
 */
public interface OliRpcRemote {

    /**
     * 发送数据到 server
     * @param req OliReq
     * @return OliRpcFuture
     */
    OliResp send(OliReq req);

}
