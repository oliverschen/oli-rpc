package com.github.oliverschen.olirpc.registry.zk;

import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.registry.Register;

import java.util.Set;

/**
 * @author ck
 */
public class ZkRegister implements Register {

    @Override
    public void register(Object bean) {
        // todo zk 注册中心
    }

    @Override
    public Set<String> obtainServices(String serviceKey) {
        // todo 获取服务
        return null;
    }

    @Override
    public Register newClient(OliProperties oliProperties) {
        // todo 创建连接
        return null;
    }
}
