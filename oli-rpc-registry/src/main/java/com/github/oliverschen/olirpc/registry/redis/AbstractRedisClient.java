package com.github.oliverschen.olirpc.registry.redis;

import com.github.oliverschen.olirpc.properties.OliProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

import static com.github.oliverschen.olirpc.constant.Constants.*;

/**
 * @author ck
 */
public abstract class AbstractRedisClient implements RedisClient {
    private static final Logger log = LoggerFactory.getLogger(AbstractRedisClient.class);

    private final JedisPool pool;
    private final String password;

    public AbstractRedisClient(OliProperties properties) {
        String host = REDIS_SERVER_HOST_DEFAULT;
        int port = REDIS_SERVER_PORT_DEFAULT;
        if (properties.getRedisHost() != null) {
            String[] allHost = properties.getRedisHost().split("//");
            if (allHost.length <= 1) {
                String[] addr = allHost[1].split(":");
                host = Optional.ofNullable(addr[0]).orElse(REDIS_SERVER_HOST_DEFAULT);
                port = Integer.parseInt(Optional.ofNullable(addr[1]).orElse(String.valueOf(REDIS_SERVER_PORT_DEFAULT)));
            }else {
                log.info("redis use default host and port");
            }
        }
        pool = new JedisPool(host, port, false);
        this.password = properties.getRedisPassword() == null ? REDIS_SERVER_PASSWORD : properties.getRedisPassword();
    }

    /**
     * Jedis 客户端
     */
    public Jedis getJedis() {
        Jedis jedis = pool.getResource();
        jedis.auth(password);
        return jedis;
    }

}
