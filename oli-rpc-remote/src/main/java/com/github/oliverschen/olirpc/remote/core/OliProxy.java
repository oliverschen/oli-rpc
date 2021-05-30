package com.github.oliverschen.olirpc.remote.core;

import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.properties.OliProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

import static com.github.oliverschen.olirpc.constant.Enums.ProxyType.JDK;

/**
 * oli-rpc client
 * @author ck
 */
public class OliProxy {

    private static final Logger log = LoggerFactory.getLogger(OliProxy.class);
    private final OliProperties oliProperties;

    public static OliProxy init(OliProperties properties) {
        return new OliProxy(properties);
    }

    public OliProxy(OliProperties properties) {
        this.oliProperties = properties;
    }

    /**
     * create proxy object default jdk proxy
     *
     * @param serviceClass target Service Interface Class
     * @param url          target Service url
     * @param <T>          service
     * @param <X>          result
     * @return proxy service object
     */
    public <T> T create(Class<T> serviceClass, String url) {
        Enums.ProxyType proxyType = Enums.ProxyType.of(oliProperties.getProxy());
        Object o;
        if (JDK.equals(proxyType)) {
            o = jdkProxy(serviceClass, url, oliProperties.getProtocol());
        }else {
            o = byByteBuddyProxy(serviceClass, url, oliProperties.getProtocol());
        }
        return (T) o;
    }

    /**
     * jdk 动态代理
     */
    public static <T> T jdkProxy(Class<T> serviceClass, String url, String protocol) {
        Object o = Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new JdkProxy<>(serviceClass, url,protocol)
        );
        return (T) o;
    }


    /**
     * byteBuddy 动态代理
     */
    public static <T> T byByteBuddyProxy(Class<T> serviceClass, String url,String protocol) {
        ByteBuddyProxy<T> proxy = new ByteBuddyProxy<>(url,protocol);
        try {
            return (T) proxy.createInstance(serviceClass);
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException e) {
            log.error("byteBuddy create instance error:", e);
            throw new OliException("create instance error");
        }
    }

}
