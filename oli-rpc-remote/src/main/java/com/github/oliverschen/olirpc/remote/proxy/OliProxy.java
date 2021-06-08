package com.github.oliverschen.olirpc.remote.proxy;

import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.protocol.OliUrl;
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


    public static OliProxy init() {
        return new OliProxy();
    }

    /**
     * create proxy object default jdk proxy
     *
     * @param serviceClass target Service Interface Class
     * @param url          target Service url
     * @param <T>          service
     * @return proxy service object
     */
    public <T> T create(OliUrl oliUrl) {
        Enums.ProxyType proxyType = Enums.ProxyType.of(oliUrl.getProxy());
        Object o;
        if (JDK.equals(proxyType)) {
            o = jdkProxy(oliUrl);
        }else {
            o = byByteBuddyProxy(oliUrl);
        }
        return (T) o;
    }

    /**
     * jdk 动态代理
     */
    public static <T> T jdkProxy(OliUrl<T> oliUrl) {
        Class<T> serviceClass = oliUrl.getServiceClass();
        Object o = Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class[]{serviceClass}, new JdkProxy<>(oliUrl));
        return (T)o;
    }


    /**
     * byteBuddy 动态代理
     */
    public static <T> T byByteBuddyProxy(OliUrl oliUrl) {
        ByteBuddyProxy<T> proxy = new ByteBuddyProxy<>(oliUrl);
        try {
            return (T) proxy.createInstance(oliUrl.getServiceClass());
        } catch (NoSuchMethodException | IllegalAccessException |
                InvocationTargetException | InstantiationException e) {
            log.error("byteBuddy create instance error:", e);
            throw new OliException("create instance error");
        }
    }

}
