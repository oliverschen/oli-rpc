package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.netty.client.OliNetty;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.github.oliverschen.olirpc.constant.Constants.NETTY_SERVER_DEFAULT_PORT;
import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;
import static net.bytebuddy.matcher.ElementMatchers.isDeclaredBy;

/**
 * @author ck
 */
public class ByteBuddyProxy<T,X> {

    private static final Logger log = LoggerFactory.getLogger(ByteBuddyProxy.class);

    private String url;
    private Class<X> result;

    public ByteBuddyProxy() {

    }

    public ByteBuddyProxy(String url, Class<X> result) {
        this.url = url;
        this.result = result;
    }

    public Object createInstance(Class<T> serviceClass) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
         return new ByteBuddy().subclass(serviceClass)
                .method(isDeclaredBy(serviceClass))
                .intercept(MethodDelegation.to(new MethodInterceptor<>(serviceClass,url,result)))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }


    public static class MethodInterceptor<T,X> extends AbstractBaseProxy{
        private final Class<T> serviceClass;
        private final String url;
        private final Class<X> result;


        public MethodInterceptor(Class<T> serviceClass, String url, Class<X> result) {
            this.serviceClass = serviceClass;
            this.url = url;
            this.result = result;
        }

        @RuntimeType
        public Object intercept(@AllArguments Object[] allArguments,
                                @Origin Method method) throws IOException {
            // intercept any method of any signature
//            OliResp oliResp = RemoteOkHttp.post(buildOliReq(serviceClass,method,allArguments), url);
            OliReq req = buildOliReq(serviceClass, method, allArguments);
            log.info("动态代理 invoke 信息：{}", req);
            OliResp oliResp = OliNetty.init(url, NETTY_SERVER_DEFAULT_PORT)
                    .connect()
                    .send(req);
            return MAPPER.readValue(oliResp.getData().toString(),this.result);
        }
    }
}
