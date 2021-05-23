package com.github.oliverschen.olirpc;

import java.util.List;
import java.util.Set;

/**
 * 路由：从注册中心获取所有服务地址
 *
 * @author ck
 */
public interface Router {

    /**
     * 按照 version group tag 等分组，首先支持 group
     * @param services all service
     * @return 统一分组的 service
     */
    List<String> route(Set<Object> services);

}
