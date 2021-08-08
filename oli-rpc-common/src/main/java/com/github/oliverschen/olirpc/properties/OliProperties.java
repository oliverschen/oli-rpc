package com.github.oliverschen.olirpc.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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

    @Autowired
    private Params params;

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
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


    @Component
    public static class Params {

        @Value("${oli.rpc.params.loadbanlance:random}")
        private String loadbanlance;
        @Value("${oli.rpc.params.router:group}")
        private String router;
        @Value("${oli.rpc.params.cluster:url}")
        private String cluster;
        @Value("${oli.rpc.params.filter:filter}")
        private String filter;
        @Value("${oli.rpc.params.filter:redis}")
        private String register;

        public String getRegister() {
            return register;
        }

        public void setRegister(String register) {
            this.register = register;
        }

        public String getLoadbanlance() {
            return loadbanlance;
        }

        public void setLoadbanlance(String loadbanlance) {
            this.loadbanlance = loadbanlance;
        }

        public String getRouter() {
            return router;
        }

        public void setRouter(String router) {
            this.router = router;
        }

        public String getCluster() {
            return cluster;
        }

        public void setCluster(String cluster) {
            this.cluster = cluster;
        }

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }
    }


}
