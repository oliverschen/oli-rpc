package com.github.oliverschen.olirpc.constant;

import com.github.oliverschen.olirpc.exception.OliException;

import java.util.Arrays;

/**
 * @author ck
 */
public class Enums {

    public enum ProxyType{
        // 代理类型，默认 JDK Proxy
        JDK,
        BYTE_BUDDY,
        ;
        public static ProxyType of(String type) {
            return Arrays.stream(ProxyType.values()).filter(p -> p.name().equals(type))
                    .findFirst().orElse(JDK);
        }
    }
}
