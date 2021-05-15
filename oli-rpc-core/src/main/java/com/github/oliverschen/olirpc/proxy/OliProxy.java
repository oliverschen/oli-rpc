package com.github.oliverschen.olirpc.proxy;

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
     * @param result       result class
     * @param <T>          service
     * @param <X>          result
     * @return proxy service object
     */
    public <T, X> T create(Class<T> serviceClass, String url, Class<X> result) {
        Enums.ProxyType proxyType = Enums.ProxyType.of(oliProperties.getProxy());
        if (JDK.equals(proxyType)) {
           return jdkProxy(serviceClass, url, result);
        }else {
            return byByteBuddyProxy(serviceClass, url, result);
        }
    }

    /**
     * jdk 动态代理
     */
    public static <T,X> T jdkProxy(Class<T> serviceClass, String url, Class<X> result) {
        Object o = Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new JdkProxy<>(serviceClass, url,result)
        );
        return (T) o;
    }


    /**
     * byteBuddy 动态代理
     */
    public static <T,X> T byByteBuddyProxy(Class<T> serviceClass,String url, Class<X> result) {
        ByteBuddyProxy<T,X> proxy = new ByteBuddyProxy<>(url, result);
        try {
            return (T) proxy.createInstance(serviceClass);
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException e) {
            log.error("byteBuddy create instance error:", e);
            throw new OliException("create instance error");
        }
    }

}
