package com.github.oliverschen.olirpc.constant;

import java.util.Arrays;

/**
 * @author ck
 */
public class Enums {

    public enum ProxyType{
        // 代理类型，默认 JDK Proxy，已实现（JDK,BYTE_BUDDY）
        JDK,
        BYTE_BUDDY,
        JAVA_SSIST,
        GCLIB,
        ASPECT_J,
        ;
        public static ProxyType of(String type) {
            return Arrays.stream(ProxyType.values()).filter(p -> p.name().equals(type))
                    .findFirst().orElse(JDK);
        }
    }

    public enum RemoteType {
        // 远程调用类型，默认 NETTY
        NETTY,
        HTTP,
        ;
        public static RemoteType of(String type) {
            return Arrays.stream(RemoteType.values()).filter(p -> p.name().equals(type))
                    .findFirst().orElse(NETTY);
        }
    }
}
