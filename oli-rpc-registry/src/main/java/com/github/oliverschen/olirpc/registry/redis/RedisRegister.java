package com.github.oliverschen.olirpc.registry.redis;

import com.github.oliverschen.olirpc.registry.export.*;
import com.github.oliverschen.olirpc.registry.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

/**
 * redis 注册中心
 * hash 结构
 * com.github.oliverschen.XXXService
 *                  http://localhost:7777@@com.github.oliverschen.XXXServiceImpl weight
 * @author ck
 */
@Component
public class RedisRegister implements Register {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ServerExport serverExport;

    /**
     * 注册到注册中心
     */
    @Override
    public void register(Object bean) {
        String subKey = serverExport.obtainImplKey(bean);
        Arrays.stream(bean.getClass().getInterfaces()).forEach(itf -> redisTemplate.opsForHash().put(itf.getName(), subKey, 0));
    }

    public Set<Object> obtainServices(String serviceKey) {
         return redisTemplate.opsForHash().keys(serviceKey);
    }

}
