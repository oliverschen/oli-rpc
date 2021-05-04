package com.github.oliverschen.olirpc.invoker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.oliverschen.olirpc.register.OliRegistry;
import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.github.oliverschen.olirpc.util.JsonUtil.MAPPER;

/**
 * invoke target service by reflection
 * @author ck
 */
public class OliInvoker {

    private static final Logger log = LoggerFactory.getLogger(OliInvoker.class);

    public static OliResp invoke(OliReq req) {
        Object bean = OliRegistry.getBean(req.getService());
        Method[] methods = bean.getClass().getMethods();
        Method method = Arrays.stream(methods)
                .filter(m -> m.getName().equals(req.getMethod()))
                .findFirst()
                .orElseThrow(() -> new OliException(req.getService() + ": " + req.getMethod() + " not find"));

        try {
            Object invoke = method.invoke(bean, req.getParams());
            return OliResp.ok(MAPPER.writeValueAsString(invoke));
        } catch (IllegalAccessException | InvocationTargetException | JsonProcessingException ex) {
            log.error("can't find the service method:", ex);
            return OliResp.error("can't find the method:" + req.getService() + req.getMethod(), ex);
        }
    }

}
