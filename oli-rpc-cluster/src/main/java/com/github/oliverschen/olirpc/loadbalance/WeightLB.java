package com.github.oliverschen.olirpc.loadbalance;

import java.util.List;

/**
 * 按照权重选择服务
 * @author ck
 */
public class WeightLB extends AbstractLoadBalance {

    @Override
    String doBalance(List<String> service) {
        // todo 按照权重进行选择服务
        return service.get(0);
    }

}
