package com.github.oliverschen.olirpc.refer;

import com.github.oliverschen.olirpc.LoadBalance;
import com.github.oliverschen.olirpc.Router;
import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.proxy.OliProxy;
import com.github.oliverschen.olirpc.registry.redis.RedisRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 获取注册中心的服务
 * @author ck
 */
@Component
public class OliReferHub {
    private static final Logger log = LoggerFactory.getLogger(OliReferHub.class);

    @Autowired
    private RedisRegister redisRegister;
    @Autowired
    private OliProperties oliProperties;
    @Autowired
    private Router router;
    @Autowired
    private LoadBalance loadBalance;

    /**
     * 随机获取注册中心服务
     */
    public <T> T create(Class<T> serviceClass) {
        Set<Object> services = redisRegister.obtainServices(serviceClass.getName());
        // router group version tag
        List<String> route = router.route(services);
        // load balance
        String url = loadBalance.balance(route);
        log.info("random service url is :{}", url);
        Object o = OliProxy.init(oliProperties).create(serviceClass, url);
        return (T) o;
    }

}
