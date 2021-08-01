package com.github.oliverschen.olirpc;

import com.github.oliverschen.olirpc.annotaion.OliSPI;

import java.util.List;

import static com.github.oliverschen.olirpc.constant.Constants.RANDOM_LOAD_BALANCE;

/**
 * 负载均衡
 *
 * @author ck
 */
@OliSPI(RANDOM_LOAD_BALANCE)
public interface LoadBalance {

    /**
     * 负载均衡
     * @param service
     * @return service
     */
    String balance(List<String> service);

}
