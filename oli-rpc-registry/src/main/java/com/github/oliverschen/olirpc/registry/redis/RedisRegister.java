package com.github.oliverschen.olirpc.registry.redis;

import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.registry.Register;
import com.github.oliverschen.olirpc.registry.export.ServerExport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis 注册中心
 * hash 结构
 * com.github.oliverschen.XXXService
 *                  http://localhost:7777@@com.github.oliverschen.XXXServiceImpl weight
 * @author ck
 */
@Component
public class RedisRegister implements Register, InitializingBean {

    @Autowired
    private ServerExport serverExport;
    @Autowired
    private OliProperties oliProperties;

    private RedisClient redisClient;

    /**
     * 注册到注册中心
     */
    @Override
    public void register(Object bean) {
        String subKey = serverExport.obtainImplKey(bean);
        Arrays.stream(bean.getClass().getInterfaces())
                .forEach(itf -> redisClient.hset(itf.getName(), subKey, "0"));
    }

    @Override
    public Set<Object> obtainServices(String serviceKey) {
        Map<String, String> keys = redisClient.hgetAll(serviceKey);
        return new HashSet<>(keys.keySet());
    }

    @Override
    public void afterPropertiesSet() {
        // todo 如果配置了多个地址，用 , 号分隔，需要实现 cluster 客户端
        this.redisClient = new SingleRedisClient(oliProperties.getRedis());
    }
}
