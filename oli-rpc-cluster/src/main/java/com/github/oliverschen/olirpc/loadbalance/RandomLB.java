package com.github.oliverschen.olirpc.loadbalance;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.github.oliverschen.olirpc.constant.Constants.JOINER;

/**
 * @author ck
 */
@Component("randomLB")
public class RandomLB extends AbstractLoadBalance {

    @Override
    public String balance(List<String> list) {
        String service = list.get(ThreadLocalRandom.current().nextInt(list.size()));
        String[] split = service.split(JOINER);
        return split[0];
    }
}
