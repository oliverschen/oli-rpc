package com.github.oliverschen.olirpc.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author ck
 */
@Configuration
public class OliProperties {

    @Value("${oli.rpc.proxy:jdk}")
    private String proxy;
    @Value("${oli.rpc.protocol:netty}")
    private String protocol;
    @Value("${oli.rpc.redis.host:localhost}")
    private String redisHost;
    @Value("${oli.rpc.redis.password:password}")
    private String redisPassword;

    @Value("${oli.rpc.params}")
    private Map<String, String> params;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

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
