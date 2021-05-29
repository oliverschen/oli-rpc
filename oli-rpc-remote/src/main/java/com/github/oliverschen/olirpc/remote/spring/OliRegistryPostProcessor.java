package com.github.oliverschen.olirpc.remote.spring;

import com.github.oliverschen.olirpc.annotaion.OliRefer;
import com.github.oliverschen.olirpc.annotaion.OliService;
import com.github.oliverschen.olirpc.remote.refer.OliReferHub;
import com.github.oliverschen.olirpc.registry.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
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
    @Autowired
    private OliReferHub oliReferHub;
    /**
     * save RPC bean
     * Map<Interface full name,Object>
     */
    private static final Map<String, Object> OLI_RPC_BEAN_MAP = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        OliService annotation = bean.getClass().getAnnotation(OliService.class);
        if (annotation != null) {
            log.info(" bean：[{}]  start inject", beanName);
            Arrays.stream(bean.getClass().getInterfaces())
                    .forEach(itf -> OLI_RPC_BEAN_MAP.put(itf.getName(),bean));
            // add to redis registry
            register.register(bean);
        }
        // 判断当前 bean 的字段是否有 @OliRefer 注解，如果有生成代理对象
        Arrays.stream(bean.getClass().getFields()).forEach(field -> {
            OliRefer refer = field.getAnnotation(OliRefer.class);
            if (Objects.nonNull(refer)) {
                log.info("当前 bean：{}, 字段:{} 生成代理对象", beanName, field);
                Object proxyBean = oliReferHub.create(bean.getClass());
                field.setAccessible(true);
                try {
                    field.set(bean, proxyBean);
                } catch (IllegalAccessException e) {
                    log.error("设置代理字段错误：", e);
                }
            }
        });
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
