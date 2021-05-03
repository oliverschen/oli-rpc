package com.github.oliverschen.olirpc.client;

import com.github.oliverschen.olirpc.proxy.JdkProxy;

import java.lang.reflect.Proxy;

/**
 * oli-rpc client
 *  1. 创建代理对象
 * @author ck
 */
public class OliRpc {

    /**
     * 创建代理对象
     */
    public static <T,X> T create(Class<T> serviceClass, String url, Class<X> result) {
        Object o = Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new JdkProxy<>(serviceClass, url,result)
        );
        return (T) o;
    }

}
