package com.github.oliverschen.olirpc.remote.refer;

import com.github.oliverschen.olirpc.cluster.Cluster;
import com.github.oliverschen.olirpc.loadbalance.LoadBalance;
import com.github.oliverschen.olirpc.router.Router;
import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.extension.OliSpiLoader;
import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import com.github.oliverschen.olirpc.registry.Register;
import com.github.oliverschen.olirpc.remote.proxy.OliProxy;
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
public class OliBus {
    private static final Logger log = LoggerFactory.getLogger(OliBus.class);


    @Autowired
    private OliProperties properties;

    /**
     * 随机获取注册中心服务
     */
    public <T> T create(Class<T> serviceClass) {
        OliProperties.Params params = properties.getParams();
        // get service from service center
        Set<String> services = OliSpiLoader.load(Register.class, params.getRegister())
                .newClient(properties)
                .obtainServices(serviceClass.getName());

        if (services.isEmpty()) {
            throw new OliException("can not find service");
        }

        // router group version tag
        List<String> route = OliSpiLoader.load(Router.class, params.getRouter())
                .route(services);

        // load balance
        String url = OliSpiLoader.load(LoadBalance.class,params.getLoadbanlance())
                .balance(route);

        // cluster
        OliUrl<T> oliUrl = OliSpiLoader.load(Cluster.class,params.getCluster())
                .obtainOliUrl(url, serviceClass,properties.getProtocol());

        log.info("random service url is :{}", url);
        return OliProxy.init().create(oliUrl);
    }

}
