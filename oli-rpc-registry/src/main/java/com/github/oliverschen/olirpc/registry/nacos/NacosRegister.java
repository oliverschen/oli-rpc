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

    }

    @Override
    public Set<String> obtainServices(String serviceKey) {
        return null;
    }

    @Override
    public Register newClient(OliProperties oliProperties) {
        return null;
    }
}
