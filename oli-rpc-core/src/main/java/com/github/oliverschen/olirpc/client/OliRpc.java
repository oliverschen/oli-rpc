package com.github.oliverschen.olirpc.client;

import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.proxy.ByteBuddyProxy;
import com.github.oliverschen.olirpc.proxy.JdkProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * oli-rpc client
 * @author ck
 */
public class OliRpc {

    private static final Logger log = LoggerFactory.getLogger(OliRpc.class);
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

    public static <T,X> T createByByteBuddy(Class<T> serviceClass,String url, Class<X> result) {
        ByteBuddyProxy<T,X> proxy = new ByteBuddyProxy<>(url, result);
        try {
            return (T) proxy.createInstance(serviceClass);
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            log.error("bytebuddy create instance error:", e);
            throw new OliException("create instance error");
        }
    }

}
