package com.github.oliverschen.olirpc.extension;

import com.github.oliverschen.olirpc.Router;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 按照策略加载
 * @author ck
 */
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



}
