package com.github.oliverschen.olirpc.refer;

import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.proxy.OliProxy;
import com.github.oliverschen.olirpc.registry.redis.RedisRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.oliverschen.olirpc.constant.Constants.JOINER;

/**
 * 获取注册中心的服务
 * @author ck
 */
@Component
public class OliRefer {
    private static final Logger log = LoggerFactory.getLogger(OliRefer.class);

    @Autowired
    private RedisRegister redisRegister;
    @Autowired
    private OliProperties oliProperties;

    /**
     * 随机获取注册中心服务
     */
    public <T,R> T create(Class<T> serviceClass, Class<R> result) {

        Set<Object> services = redisRegister.obtainServices(serviceClass.getName());
        List<Object> list = new ArrayList<>(services);
        String service = (String) list.get(ThreadLocalRandom.current().nextInt(list.size()));
        String[] split = service.split(JOINER);
        log.info("random service url is :{}",split[0]);
        Object o = OliProxy.init(oliProperties).create(serviceClass, split[0], result);
        return (T) o;
    }

}
