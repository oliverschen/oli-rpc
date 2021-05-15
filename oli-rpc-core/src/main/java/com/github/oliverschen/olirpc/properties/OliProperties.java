package com.github.oliverschen.olirpc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ck
 */
@Configuration
@ConfigurationProperties(prefix = "oli.rpc")
public class OliProperties {

    private String proxy;



    public String getProxy() {
        return proxy;
    }
    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

}
