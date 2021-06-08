package com.github.oliverschen.olirpc.remote.proxy;

import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import com.github.oliverschen.olirpc.remote.OliRpcRemoteBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * JDK Proxy
 *
 * @author ck
 */
public class JdkProxy<T> extends AbstractBaseProxy implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(JdkProxy.class);

    private final OliUrl<T> oliUrl;

    public JdkProxy(OliUrl<T> oliUrl) {
        this.oliUrl = oliUrl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        OliReq req = buildOliReq(oliUrl.getServiceClass(), method, args);
        log.info("动态代理 invoke 信息：{}", req);
        OliResp oliResp = OliRpcRemoteBase.init0(oliUrl)
                .send(req);
        return oliResp != null ? MAPPER.convertValue(oliResp.getData(), oliResp.getReturnType()) : null;
    }
}
