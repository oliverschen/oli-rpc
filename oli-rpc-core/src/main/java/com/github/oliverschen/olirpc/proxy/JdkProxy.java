package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.OliRpcRemoteBase;
import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.constant.Constants.NETTY_SERVER_DEFAULT_PORT;
import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * JDK Proxy
 *
 * @author ck
 */
public class JdkProxy<T,R> extends AbstractBaseProxy implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(JdkProxy.class);

    private final Class<T> serviceClass;
    private final String url;
    private final Class<R> result;
    private final String protocol;

    public JdkProxy(Class<T> serviceClass, String url, Class<R> result, String protocol) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.result = result;
        this.protocol = protocol;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        OliReq req = buildOliReq(serviceClass, method, args);
        log.info("动态代理 invoke 信息：{}", req);
        OliResp oliResp = OliRpcRemoteBase.init0(url, NETTY_SERVER_DEFAULT_PORT, protocol)
                .send(req);
        return oliResp != null ? MAPPER.readValue(oliResp.getData().toString(), this.result) : null;
    }
}
