package com.github.oliverschen.olirpc.remote.refer;

import com.github.oliverschen.olirpc.Cluster;
import com.github.oliverschen.olirpc.LoadBalance;
import com.github.oliverschen.olirpc.Router;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import com.github.oliverschen.olirpc.registry.redis.RedisRegister;
import com.github.oliverschen.olirpc.remote.proxy.OliProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 获取注册中心的服务
 * @author ck
 */
@Component
public class OliBus {
    private static final Logger log = LoggerFactory.getLogger(OliBus.class);

    @Autowired
    private RedisRegister redisRegister;
    @Autowired
    @Qualifier("group")
    private Router router;
    @Autowired
    @Qualifier("randomLB")
    private LoadBalance loadBalance;
    @Autowired
    private Cluster cluster;

    /**
     * 随机获取注册中心服务
     */
    public <T> T create(Class<T> serviceClass) {
        // get service from service center
        Set<String> services = redisRegister.obtainServices(serviceClass.getName());
        // router group version tag
        // todo 多个 router 时平滑切换 考虑使用 dubbo spi 机制实现
        List<String> route = router.route(services);
        // load balance
        String url = loadBalance.balance(route);
        // cluster
        OliUrl<T> oliUrl = cluster.obtainOliUrl(url,serviceClass);

        log.info("random service url is :{}", url);
        return OliProxy.init().create(oliUrl);
    }

}
