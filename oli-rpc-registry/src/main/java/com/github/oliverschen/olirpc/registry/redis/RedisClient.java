package com.github.oliverschen.olirpc.registry.redis;

import redis.clients.jedis.JedisPubSub;

import java.util.Map;

/**
 * from dubbo
 * https://github.com/apache/dubbo/tree/master/dubbo-remoting/dubbo-remoting-redis/src/main/java/org/apache/dubbo/remoting/redis
 * @author ck
 */
public interface RedisClient {

    Long hset(String key, String field, String value);

    void expired(String key,Integer ttl);

    String hget(String key,String field);

    Long hdel(final String key, final String... fields);

    Map<String, String> hgetAll(String key);

    Long publish(String channel, String message);

    void psubscribe(final JedisPubSub jedisPubSub, final String... patterns);

    void disconnect();

    boolean isConnected();

    void destroy();

}
