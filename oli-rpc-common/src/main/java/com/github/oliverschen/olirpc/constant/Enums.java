package com.github.oliverschen.olirpc.constant;

import java.util.Arrays;

/**
 * @author ck
 */
public class Enums {

    public enum ProxyType{
        // 代理类型，默认 JDK Proxy，已实现（JDK,BYTE_BUDDY）
        JDK("jdk"),
        BYTE_BUDDY("byteBuddy"),
        JAVA_SSIST("javassist"),
        CGLIB("cglib"),
        ASPECT_J("aspectj"),
        ;
        ProxyType(String key){
            this.key = key;
        }
        private final String key;

        public String getKey() {
            return key;
        }
        public static ProxyType of(String type) {
            return Arrays.stream(ProxyType.values()).filter(p -> p.getKey().equals(type))
                    .findFirst().orElse(JDK);
        }
    }

    public enum RemoteType {
        // 远程调用类型，默认 NETTY
        NETTY("netty"),
        HTTP("http"),
        ;
        private final String key;
        RemoteType(String key){
            this.key = key;
        }
        public String getKey() {
            return key;
        }
        public static RemoteType of(String type) {
            return Arrays.stream(RemoteType.values()).filter(p -> p.getKey().equals(type))
                    .findFirst().orElse(NETTY);
        }
    }

    public enum RedisType {
        // Redis 类型
        SINGLE,
        CLUSTER,
        ;
        public static RedisType of(String type) {
            return Arrays.stream(RedisType.values()).filter(p -> p.name().equals(type))
                    .findFirst().orElse(SINGLE);
        }

    }
}
