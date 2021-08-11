package com.github.oliverschen.olirpc.loadbalance;

import com.github.oliverschen.olirpc.exception.OliException;

import java.util.List;

/**
 * 按照权重选择服务
 * @author ck
 */
public class WeightLB extends AbstractLoadBalance {

    @Override
    String doBalance(List<String> service) {
        // todo 按照权重进行选择服务
        if (service.isEmpty()) {
            throw new OliException("provider is empty");
        }
        return service.get(0);
    }

}
