package com.github.oliverschen.olirpc.remote.core;

import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.remote.OliRpcRemoteBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.constant.Constants.NETTY_SERVER_DEFAULT_PORT;

/**
 * JDK Proxy
 *
 * @author ck
 */
public class JdkProxy<T> extends AbstractBaseProxy implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(JdkProxy.class);

    private final Class<T> serviceClass;
    private final String url;
    private final String protocol;

    public JdkProxy(Class<T> serviceClass, String url,String protocol) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.protocol = protocol;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        OliReq req = buildOliReq(serviceClass, method, args);
        log.info("动态代理 invoke 信息：{}", req);
        return OliRpcRemoteBase.init0(url, NETTY_SERVER_DEFAULT_PORT, protocol)
                .send(req);
    }
}
