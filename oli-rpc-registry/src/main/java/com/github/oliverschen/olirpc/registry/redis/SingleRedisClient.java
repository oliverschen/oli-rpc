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
        Long result = jedis.hset(key, field, value);
        jedis.close();
        return result;
    }

    @Override
    public String hget(String key,String field) {
        String result = jedis.hget(key, field);
        jedis.close();
        return result;
    }

    @Override
    public Long hdel(String key, String... fields) {
        Long result = jedis.hdel(key, fields);
        jedis.close();
        return result;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Map<String, String> result = jedis.hgetAll(key);
        jedis.close();
        return result;
    }

    @Override
    public Long publish(String channel, String message) {
        Long result = jedis.publish(channel, message);
        jedis.close();
        return result;
    }

    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String... patterns) {
        jedis.psubscribe(jedisPubSub, patterns);
        jedis.close();
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
