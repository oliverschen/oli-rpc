package com.github.oliverschen.olirpc.client;

import com.github.oliverschen.olirpc.constant.Constants;
import com.github.oliverschen.olirpc.register.RedisRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.oliverschen.olirpc.constant.Constants.JOINER;

/**
 * 获取注册中心的服务
 * @author ck
 */
@Configuration
public class OliReference {
    private static final Logger log = LoggerFactory.getLogger(OliReference.class);

    @Autowired
    private RedisRegister redisRegister;

    /**
     * 随机获取注册中心服务
     */
    public <T,X> T create(Class<T> serviceClass, Class<X> result) {
        Set<Object> services = redisRegister.obtainServices(serviceClass.getName());
        List<Object> list = new ArrayList<>(services);
        String service = (String) list.get(ThreadLocalRandom.current().nextInt(list.size()));
        String[] split = service.split(JOINER);
        log.info("random service url is :{}",split[0]);
        Object o = OliRpc.create(serviceClass, split[0], result);
        return (T) o;
    }

}
