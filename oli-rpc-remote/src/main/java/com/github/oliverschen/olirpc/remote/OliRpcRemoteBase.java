package com.github.oliverschen.olirpc.remote;

import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.remote.http.OkHttpRemote;
import com.github.oliverschen.olirpc.remote.netty.client.OliNetty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.oliverschen.olirpc.constant.Enums.RemoteType.NETTY;

/**
 * abstract 类
 *
 * @author ck
 */
public class OliRpcRemoteBase {
    private static final Logger log = LoggerFactory.getLogger(OliRpcRemoteBase.class);

    /**
     * 远程服务
     * url：http://localhost:8080/
     */
    public static OliRpcRemote init0(String url,String protocol) {
        Enums.RemoteType remoteType = Enums.RemoteType.of(protocol);
        log.info("init0 method url is :{}", url);
        if (NETTY.equals(remoteType)) {
            String[] address = url.split("//")[1].split(":");
            return OliNetty.init(address[0], Integer.parseInt(address[1])).connect();
        } else {
            return OkHttpRemote.init(url);
        }
    }
}
