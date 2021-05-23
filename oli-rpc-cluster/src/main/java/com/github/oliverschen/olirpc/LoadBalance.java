package com.github.oliverschen.olirpc;

import java.util.List;

/**
 * 负载均衡
 *
 * @author ck
 */
public interface LoadBalance {

    /**
     * 负载均衡
     * @param service
     * @return service
     */
    String balance(List<String> service);

}
