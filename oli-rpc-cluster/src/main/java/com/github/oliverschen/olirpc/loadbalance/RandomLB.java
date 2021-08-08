package com.github.oliverschen.olirpc.loadbalance;

import com.github.oliverschen.olirpc.exception.OliException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.oliverschen.olirpc.constant.Constants.JOINER;

/**
 * @author ck
 */
public class RandomLB extends AbstractLoadBalance {

    @Override
    String doBalance(List<String> list) {
        if (list.isEmpty()) {
            throw new OliException("service is empty");
        }
        String service = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        String[] split = service.split(JOINER);
        return split[0];
    }
}
