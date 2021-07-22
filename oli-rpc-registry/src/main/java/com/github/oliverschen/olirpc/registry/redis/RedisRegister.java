package com.github.oliverschen.olirpc.registry.redis;

import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.registry.Register;
import com.github.oliverschen.olirpc.registry.export.ServerExport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static com.github.oliverschen.olirpc.constant.Constants.REDIS_REGISTRY_KEY_TTL;

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
    private OliProperties oliProperties;

    private RedisClient redisClient;

    /**
     * 注册到注册中心
     */
    @Override
    public void register(Object bean) {
        String subKey = ServerExport.init().obtainImplKey(bean);
        Arrays.stream(bean.getClass().getInterfaces())
                .forEach(itf -> {
                    redisClient.hset(itf.getName(), subKey, "0");
                    redisClient.expired(itf.getName(), REDIS_REGISTRY_KEY_TTL);
                });
        // todo 发布订阅刷新过期时间
    }

    @Override
    public Set<String> obtainServices(String serviceKey) {
        Map<String, String> keys = redisClient.hgetAll(serviceKey);
        return keys.keySet();
    }

    @Override
    public void afterPropertiesSet() {
        // todo 如果配置了多个地址，用 , 号分隔，需要实现 cluster 客户端
        this.redisClient = new SingleRedisClient(oliProperties);
    }
}
