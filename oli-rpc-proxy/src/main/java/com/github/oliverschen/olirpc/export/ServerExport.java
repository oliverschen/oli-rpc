package com.github.oliverschen.olirpc.export;

import com.github.oliverschen.olirpc.exception.OliException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.github.oliverschen.olirpc.constant.Constants.*;

/**
 * @author ck
 */
@Configuration
public class ServerExport {

    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String contextPath;


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
            e.printStackTrace();
            throw new OliException("host is not found");
        }
        return DEFAULT_HOST.replace(LOCAL_HOST, hostAddress) + port + JOINER + bean.getClass();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
}
