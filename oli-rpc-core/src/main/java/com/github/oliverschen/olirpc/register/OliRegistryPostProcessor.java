package com.github.oliverschen.olirpc.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * oli-rpc context
 * save the @Service annotation beans in OLI_RPC_BEAN_MAP map at IoC container startup
 * @author ck
 */
@Component
public class OliRegistryPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(OliRegistryPostProcessor.class);

    @Autowired
    private Register register;
    /**
     * save RPC bean
     * Map<Interface full name,Object>
     */
    private static final Map<String, Object> OLI_RPC_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        Service annotation = bean.getClass().getAnnotation(Service.class);
        if (annotation != null) {
            log.info(" bean：[{}]  start inject", beanName);
            Arrays.stream(bean.getClass().getInterfaces())
                    .forEach(itf -> OLI_RPC_BEAN_MAP.put(itf.getName(),bean));
            // add to redis registry
            register.register(bean);
        }
        return bean;
    }

    /**
     * get rpc bean by Service class full name
     */
    public static Object getBean(String beanName) {
        Object o = OLI_RPC_BEAN_MAP.get(beanName);
        if (o == null) {
            log.error("not found rpc bean {}", beanName);
        }
        return o;
    }
}
