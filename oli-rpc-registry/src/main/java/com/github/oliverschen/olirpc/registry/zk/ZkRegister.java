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
