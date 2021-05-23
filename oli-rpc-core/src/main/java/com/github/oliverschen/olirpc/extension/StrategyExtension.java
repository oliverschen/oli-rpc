package com.github.oliverschen.olirpc.extension;

import com.github.oliverschen.olirpc.LoadBalance;
import com.github.oliverschen.olirpc.Router;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 按照策略加载
 * @author ck
 */
@Component
public class StrategyExtension extends AbstractExtension {


    /**
     * 路由 extension
     * @param routers
     */
    public void router(List<Router> routers) {
        routers.forEach(router -> {
            Component component = router.getClass().getAnnotation(Component.class);
            ROUTER.putIfAbsent(component.value(), router);
        });
    }

    /**
     * 负载均衡 extension
     * @param loadBalances
     */
    public void loadBalance(List<LoadBalance> loadBalances) {
        loadBalances.forEach(loadBalance -> {
            Component component = loadBalance.getClass().getAnnotation(Component.class);
            LOAD_BALANCE.putIfAbsent(component.value(), loadBalance);
        });
    }



}
