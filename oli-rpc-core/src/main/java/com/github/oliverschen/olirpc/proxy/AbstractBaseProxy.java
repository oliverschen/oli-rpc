package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.request.OliReq;

import java.lang.reflect.Method;

/**
 * @author ck
 */
public abstract class AbstractBaseProxy {

    protected <T> OliReq buildOliReq(Class<T> serviceClass, Method method,Object[] args) {
        OliReq req = new OliReq();
        req.setService(serviceClass.getName());
        req.setMethod(method.getName());
        req.setParams(args);
        return req;
    }
}
