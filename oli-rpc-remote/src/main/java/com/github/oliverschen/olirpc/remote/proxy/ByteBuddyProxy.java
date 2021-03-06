package com.github.oliverschen.olirpc.remote.proxy;

import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import com.github.oliverschen.olirpc.remote.OliRpcRemoteBase;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

/**
 * byteBuddy 动态代理
 * T: 目标类
 * R: 返回结果类
 * @author ck
 */
public class ByteBuddyProxy<T> {
    private static final Logger log = LoggerFactory.getLogger(ByteBuddyProxy.class);

    private final OliUrl<T> oliUrl;

    public ByteBuddyProxy(OliUrl<T> oliUrl) {
        this.oliUrl = oliUrl;
    }

    public Object createInstance(Class<T> serviceClass) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
         return new ByteBuddy().subclass(serviceClass)
                .method(isDeclaredBy(serviceClass))
                .intercept(MethodDelegation.to(new MethodInterceptor<>(oliUrl)))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }


    /**
     * byteBuddy 处理器类
     */
    public static class MethodInterceptor<T> extends AbstractBaseProxy {
        private final OliUrl<T> oliUrl;

        public MethodInterceptor(OliUrl<T> oliUrl) {
            this.oliUrl = oliUrl;
        }

        @RuntimeType
        public Object intercept(@AllArguments Object[] allArguments,
                                @Origin Method method) {
            OliReq req = buildOliReq(oliUrl.getServiceClass(), method, allArguments);
            log.info("动态代理 invoke 信息：{}", req);
            OliResp oliResp = OliRpcRemoteBase.init0(oliUrl).send(req);
            return oliResp != null ? oliResp.getData() : null;
        }
    }
}
