package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.RemoteOkHttp;
import com.github.oliverschen.olirpc.response.OliResp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * JDK Proxy
 *
 * @author ck
 */
public class JdkProxy<T,X> extends AbstractBaseProxy implements InvocationHandler {
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
        OliResp oliResp = RemoteOkHttp.post(buildOliReq(serviceClass,method,args), url);
        return MAPPER.readValue(oliResp.getData().toString(),this.result);
    }
}
