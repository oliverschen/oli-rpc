package com.github.oliverschen.olirpc.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ck
 */
@Configuration
public class OliProperties {

    @Value("oli.rpc.proxy")
    private String proxy;
    @Value("oli.rpc.protocol")
    private String protocol;


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProxy() {
        return proxy;
    }
    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

}
