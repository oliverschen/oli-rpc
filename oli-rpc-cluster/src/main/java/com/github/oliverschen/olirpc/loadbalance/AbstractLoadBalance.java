package com.github.oliverschen.olirpc.loadbalance;

import java.util.List;

/**
 * @author ck
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String balance(List<String> service) {

        return doBalance(service);
    }

    /**
     * 各个负载均衡的逻辑
     * @param service service
     * @return String
     */
    abstract String doBalance(List<String> service);
}
