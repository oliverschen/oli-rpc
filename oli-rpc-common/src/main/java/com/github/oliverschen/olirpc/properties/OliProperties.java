package com.github.oliverschen.olirpc.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ck
 */
@Configuration
public class OliProperties {

    @Value("${oli.rpc.proxy}")
    private String proxy;
    @Value("${oli.rpc.protocol}")
    private String protocol;
    private Redis redis = new Redis();

    public static class Redis {
        /**
         * redis://localhost:6379
         */
        @Value("${oli.rpc.redis.host}")
        private String host;
        @Value("${oli.rpc.redis.password}")
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
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
