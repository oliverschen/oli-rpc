package com.github.oliverschen.olirpc.registry.nacos;

import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.registry.Register;

import java.util.Set;

/**
 * 集成 nacos 注册中心
 * @author ck
 */
public class NacosRegister implements Register {

    @Override
    public void register(Object bean) {
        // todo 注册服务
    }

    @Override
    public Set<String> obtainServices(String serviceKey) {
        // TODO nacos 注册中心
        return null;
    }

    @Override
    public Register newClient(OliProperties oliProperties) {
        // todo nacoa 客户端
        return null;
    }
}
