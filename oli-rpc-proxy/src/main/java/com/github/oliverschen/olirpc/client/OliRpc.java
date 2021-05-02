package com.github.oliverschen.olirpc.client;

import com.github.oliverschen.olirpc.proxy.JdkProxy;

import java.lang.reflect.Proxy;

/**
 * oli-rpc client
 *  1. 创建代理对象
 * @author ck
 */
public class OliRpc<T> {

    /**
     * 创建代理对象
     */
    public T create(Class<T> serviceClass,String url) {
        Object o = Proxy.newProxyInstance(
                OliRpc.class.getClassLoader(),
                new Class[]{serviceClass},
                new JdkProxy<>(serviceClass, url)
        );
        return (T) o;
    }

}
