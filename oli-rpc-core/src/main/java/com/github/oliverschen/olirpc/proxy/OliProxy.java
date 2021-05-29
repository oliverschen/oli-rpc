package com.github.oliverschen.olirpc.proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.protocol.OliResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.Objects;

import static com.github.oliverschen.olirpc.constant.Enums.ProxyType.JDK;
import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

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
        try {
            Object o;
            if (JDK.equals(proxyType)) {
                o = jdkProxy(serviceClass, url, oliProperties.getProtocol());
            }else {
                o = byByteBuddyProxy(serviceClass, url, oliProperties.getProtocol());
            }
            String result = MAPPER.writeValueAsString(o);
            OliResp oliResp = MAPPER.readValue(result, OliResp.class);
            return (T) oliResp.getData();
        } catch (JsonProcessingException e) {
            log.error("代理结果转换异常", e);
            throw new OliException("代理结果转换异常");
        }
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
