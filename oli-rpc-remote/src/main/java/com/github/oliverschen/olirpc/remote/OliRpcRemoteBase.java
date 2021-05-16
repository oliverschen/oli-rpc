package com.github.oliverschen.olirpc.remote;

import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.remote.http.OkHttpRemote;
import com.github.oliverschen.olirpc.remote.netty.client.OliNetty;

import static com.github.oliverschen.olirpc.constant.Enums.RemoteType.NETTY;

/**
 * abstract 类
 *
 * @author ck
 */
public class OliRpcRemoteBase {

    /**
     * 远程服务
     * url：http://localhost:8080/
     */
    public static OliRpcRemote init0(String url, Integer port, String protocol) {
        Enums.RemoteType remoteType = Enums.RemoteType.of(protocol);
        if (NETTY.equals(remoteType)) {
            String[] address = url.split("//")[1].split(":");
            return OliNetty.init(address[0], port).connect();
        } else {
            return OkHttpRemote.init(url);
        }
    }
}
