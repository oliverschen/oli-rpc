package com.github.oliverschen.olirpc.loadbalance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.oliverschen.olirpc.constant.Constants.JOINER;

/**
 * @author ck
 */
public class RandomLB extends AbstractLoadBalance {

    @Override
    String doBalance(List<String> list) {
        String service = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        String[] split = service.split(JOINER);
        return split[0];
    }
}
