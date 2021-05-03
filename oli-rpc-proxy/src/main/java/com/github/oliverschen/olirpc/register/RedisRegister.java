package com.github.oliverschen.olirpc.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis 注册中心
 * hash 结构
 * com.github.oliverschen.XXXService:xxxMethod  ip:port
 * @author ck
 */
public class RedisRegister {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 注册到注册中心
     */
    public void register(String serviceName,String destination,Integer weight) {
        redisTemplate.opsForHash().put(serviceName,destination,weight);
    }


}
