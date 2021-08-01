package com.github.oliverschen.olirpc.constant;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ck
 */
public final class Constants {

    private Constants(){}

    public static final String DEFAULT_HOST = "http://localhost:";
    public static final String LOCAL_HOST = "localhost";
    public static final String JOINER = "@@";

    public static final String URL_SPLIT = "//";
    public static final String URL_COLON = ":";
    public static final String OLI_TOMCAT_PATH = "/*";
    public static final String EMPTY_STR = "";
    public static final Integer SPLIT_SIZE = 2;

    /**
     * redis
     */
    public static final String REDIS_SERVER_HOST_DEFAULT = "localhost";
    public static final int REDIS_SERVER_PORT_DEFAULT = 6379;
    public static final String REDIS_SERVER_PASSWORD = "redis";
    public static final Integer REDIS_REGISTRY_KEY_TTL = 90;

    /**
     * netty 相关
     */
    public static final Integer NETTY_SERVER_DEFAULT_PORT = ThreadLocalRandom.current().nextInt(10000, 20000);

    public static final AtomicLong UNION_ID = new AtomicLong(0);

    /**
     * 配置相关
     */
    public static final String BASE_PACKAGE = "basePackage";

    /**
     * SPI 相关
     */
    public static final String RANDOM_LOAD_BALANCE = "random";
}
