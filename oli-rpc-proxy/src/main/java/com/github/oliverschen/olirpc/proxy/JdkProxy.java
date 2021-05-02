package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.RemoteOkHttp;
import com.github.oliverschen.olirpc.request.OliReq;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 动态代理类
 *
 * @author ck
 */
public class JdkProxy<T> implements InvocationHandler {
    private Class<T> serviceClass;
    private String url;

    public JdkProxy() { }

    public JdkProxy(Class<T> serviceClass,String url) {
        this.serviceClass = serviceClass;
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 包装标准请求体
        OliReq req = new OliReq();
        req.setService(proxy.getClass().getSimpleName());
        req.setMethod(method.getName());
        req.setParams(args);
        // 发送请求
       return RemoteOkHttp.post(req, url);
    }

    public Class<T> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
