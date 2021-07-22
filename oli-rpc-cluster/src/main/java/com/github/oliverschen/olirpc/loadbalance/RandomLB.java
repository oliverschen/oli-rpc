package com.github.oliverschen.olirpc.loadbalance;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ck
 */
@Component
public class RandomLB extends AbstractLoadBalance {

    @Override
    public String balance(List<String> list) {
        return super.balance(list);
    }
}
