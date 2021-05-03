package com.github.oliverschen.olirpc.client;

import com.github.oliverschen.olirpc.proxy.JdkProxy;

import java.lang.reflect.Proxy;

/**
 * oli-rpc client
 * @author ck
 */
public class OliRpc {

    /**
     * create proxy object
     * @param serviceClass target Service Interface Class
     * @param url target Service url
     * @param result result class
     * @param <T>  service
     * @param <X> result
     * @return proxy service object
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
