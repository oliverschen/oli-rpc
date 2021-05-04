package com.github.oliverschen.olirpc.register;

/**
 * @author ck
 */
public interface Register {

    /**
     * 注册服务到注册中心
     * @param bean 要注册的服务
     */
    void register(Object bean);

}
