package com.github.oliverschen.olirpc.registry.redis;

import com.github.oliverschen.olirpc.properties.OliProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

/**
 * jedis client from dubbo
 * https://github.com/apache/dubbo/tree/master/dubbo-remoting/dubbo-remoting-redis/src/main/java/org/apache/dubbo/remoting/redis
 * @author ck
 */
public class SingleRedisClient extends AbstractRedisClient {

    private final Jedis jedis;

    public SingleRedisClient(OliProperties properties) {
        super(properties);
        jedis = getJedis();
    }

    @Override
    public Long hset(String key, String field, String value) {
        return jedis.hset(key, field, value);
    }

    @Override
    public void expired(String key, Integer ttl) {
        jedis.expire(key, ttl);
    }

    @Override
    public String hget(String key,String field) {
        return jedis.hget(key, field);
    }

    @Override
    public Long hdel(String key, String... fields) {
        return jedis.hdel(key, fields);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return jedis.hgetAll(key);
    }

    @Override
    public Long publish(String channel, String message) {
        return jedis.publish(channel, message);
    }

    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        jedis.psubscribe(jedisPubSub, patterns);
    }

    @Override
    public void disconnect() {
        jedis.close();
    }

    @Override
    public boolean isConnected() {
        boolean result = jedis.isConnected();
        jedis.close();
        return result;
    }

    @Override
    public void destroy() {
        jedis.close();
    }
}
