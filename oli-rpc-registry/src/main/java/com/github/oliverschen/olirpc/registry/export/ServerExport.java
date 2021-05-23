package com.github.oliverschen.olirpc.registry.export;

import com.github.oliverschen.olirpc.exception.OliException;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.github.oliverschen.olirpc.constant.Constants.*;


/**
 * @author ck
 */
public class ServerExport {

    private String port;

    public ServerExport() {
    }

    public ServerExport(String port) {
        this.port = port;
    }

    public static ServerExport init(String port){
        return new ServerExport(port);
    }
    /**
     * obtain implement Service url. eg:
     * http://localhost:7777@@com.github.oliverschen.XXXServiceImpl weight
     * @return string
     * @throws UnknownHostException
     */
    public String obtainImplKey(Object bean) {
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new OliException("host is not found");
        }
        return DEFAULT_HOST.replace(LOCAL_HOST, hostAddress) + port + JOINER + bean.getClass();
    }
}
