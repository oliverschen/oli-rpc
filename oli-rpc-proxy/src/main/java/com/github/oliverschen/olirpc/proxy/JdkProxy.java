package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.RemoteOkHttp;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * JDK 动态代理类
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
        // 包装标准请求体
        OliReq req = new OliReq();
        req.setService(serviceClass.getName());
        req.setMethod(method.getName());
        req.setParams(args);
        // 发送请求
        OliResp oliResp = RemoteOkHttp.post(req, url);
        return MAPPER.readValue(oliResp.getData().toString(),this.result);
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
