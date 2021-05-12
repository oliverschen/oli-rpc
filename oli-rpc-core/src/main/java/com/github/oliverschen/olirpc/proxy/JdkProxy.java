package com.github.oliverschen.olirpc.proxy;

import com.github.oliverschen.olirpc.remote.netty.client.OliNetty;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
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
public class JdkProxy<T,X> extends AbstractBaseProxy implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(JdkProxy.class);

    private Class<T> serviceClass;
    private String url;
    private Class<X> result;

    public JdkProxy() { }

    public JdkProxy(Class<T> serviceClass,String url,Class<X> result) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.result = result;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        OliReq req = buildOliReq(serviceClass, method, args);
        log.info("动态代理 invoke 信息：{}", req);
        OliResp oliResp = OliNetty.init(url, NETTY_SERVER_DEFAULT_PORT)
                .connect()
                .send(req);

        // http 请求，todo 后面需要写成自适应的方式来调用
//        OliResp oliResp = RemoteOkHttp.post(buildOliReq(serviceClass, method, args), url);

        return oliResp != null ? MAPPER.readValue(oliResp.getData().toString(), this.result) : null;
    }
}
