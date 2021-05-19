package com.github.oliverschen.olirpc.registry;

import java.util.Set;

/**
 * @author ck
 */
public interface Register {

    /**
     * 注册服务到注册中心
     * @param bean 要注册的服务
     */
    void register(Object bean);

    /**
     * 获取 serviceKey 对应的服务列表
     *
     * @param serviceKey 注册中心 key
     * @return Set<Object> 多个实例上的 bean
     */
    Set<Object> obtainServices(String serviceKey);

}
