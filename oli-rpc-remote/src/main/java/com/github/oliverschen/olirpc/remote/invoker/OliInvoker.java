package com.github.oliverschen.olirpc.remote.invoker;

import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;
import com.github.oliverschen.olirpc.remote.spring.OliRegistryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * invoke target service by reflection
 * @author ck
 */
public class OliInvoker {
    private static final Logger log = LoggerFactory.getLogger(OliInvoker.class);
    private OliInvoker() {
        throw new IllegalStateException("Utility class");
    }

    public static OliResp invoke(OliReq req) {
        Object bean = OliRegistryPostProcessor.getBean(req.getService());
        Method[] methods = bean.getClass().getMethods();
        Method method = Arrays.stream(methods)
                .filter(m -> m.getName().equals(req.getMethod()))
                .findFirst()
                .orElseThrow(() -> new OliException(req.getService() + ": " + req.getMethod() + " not find"));
        Class<?> returnType = method.getReturnType();
        try {
            Object invoke = method.invoke(bean, req.getParams());
            OliResp resp = OliResp.ok(invoke);
            resp.setReturnType(returnType);
            return resp;
        } catch (IllegalAccessException | InvocationTargetException ex) {
            log.error("can't find the service method:", ex);
            return OliResp.error("can't find the method:" + req.getService() + req.getMethod(), ex);
        }
    }

}
