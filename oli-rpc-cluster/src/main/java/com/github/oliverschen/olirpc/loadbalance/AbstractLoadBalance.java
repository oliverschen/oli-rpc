package com.github.oliverschen.olirpc.loadbalance;

import com.github.oliverschen.olirpc.LoadBalance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.oliverschen.olirpc.constant.Constants.JOINER;

/**
 * @author ck
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    @Override
    public String balance(List<String> list) {
        String service = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        String[] split = service.split(JOINER);
        return split[0];
    }
}
