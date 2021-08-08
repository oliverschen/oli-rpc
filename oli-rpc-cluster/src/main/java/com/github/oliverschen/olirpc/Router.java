package com.github.oliverschen.olirpc;

import com.github.oliverschen.olirpc.annotaion.OliSPI;

import java.util.List;
import java.util.Set;

import static com.github.oliverschen.olirpc.constant.Constants.GROUP_ROUTER;

/**
 * 路由：从注册中心获取所有服务地址
 * // todo 路由
 * @author ck
 */
@OliSPI(GROUP_ROUTER)
public interface Router {

    /**
     * 按照 version group tag 等分组，首先支持 group
     * @param services all service
     * @return 统一分组的 service
     */
    List<String> route(Set<String> services);

}
