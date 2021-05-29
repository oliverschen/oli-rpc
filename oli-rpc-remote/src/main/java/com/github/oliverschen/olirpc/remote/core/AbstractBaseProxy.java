package com.github.oliverschen.olirpc.remote.core;

import com.github.oliverschen.olirpc.protocol.OliReq;

import java.lang.reflect.Method;

/**
 * @author ck
 */
public abstract class AbstractBaseProxy {

    /**
     * 构造 OliReq
     */
    protected <T> OliReq buildOliReq(Class<T> serviceClass, Method method,Object[] args) {
        OliReq req = new OliReq();
        req.setService(serviceClass.getName());
        req.setMethod(method.getName());
        req.setParams(args);
        return req;
    }
}
