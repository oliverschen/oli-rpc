package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.RemoteOkHttp;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * JDK Proxy
 *
 * @author ck
 */
public class JdkProxy<T,X> implements InvocationHandler {
    private Class<T> serviceClass;
    private String url;
    private Class<X> result;

    public JdkProxy() { }

    public JdkProxy(Class<T> serviceClass,String url,Class<X> result) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.result = result;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        OliReq req = new OliReq();
        req.setService(serviceClass.getName());
        req.setMethod(method.getName());
        req.setParams(args);
        OliResp oliResp = RemoteOkHttp.post(req, url);
        return MAPPER.readValue(oliResp.getData().toString(),this.result);
    }
}
