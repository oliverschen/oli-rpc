package com.github.oliverschen.olirpc.constant;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ck
 */
public final class Constants {

    private Constants(){}




    public static final String DEFAULT_HOST = "http://localhost:";
    public static final String LOCAL_HOST = "localhost";
    public static final String JOINER = "@@";

    /**
     * netty 相关
     */
    public static final Integer NETTY_SERVER_DEFAULT_PORT = 6789;

    public static final AtomicLong UNION_ID = new AtomicLong(0);
}
