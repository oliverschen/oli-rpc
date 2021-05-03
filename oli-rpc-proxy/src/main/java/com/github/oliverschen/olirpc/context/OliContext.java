package com.github.oliverschen.olirpc.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ck
 */
@Component
public class OliContext implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(OliContext.class);

    /**
     * save RPC bean
     * Map<Interface name,Object>
     */
    private static final Map<String, Object> RPC_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Service annotation = bean.getClass().getAnnotation(Service.class);
        if (annotation != null) {
            log.info(" bean：[{}]  start inject", beanName);
            Arrays.stream(bean.getClass().getInterfaces())
                    .forEach(itf -> RPC_BEAN_MAP.put(itf.getName(),bean));
        }
        return bean;
    }

    /**
     * get rpc bean
     */
    public static Object getBean(String beanName) {
        Object o = RPC_BEAN_MAP.get(beanName);
        if (o == null) {
            log.error("not found rpc bean {}", beanName);
        }
        return o;
    }
}
