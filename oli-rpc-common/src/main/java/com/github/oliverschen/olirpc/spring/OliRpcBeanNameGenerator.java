package com.github.oliverschen.olirpc.spring;

import com.github.oliverschen.olirpc.annotaion.OliRefer;
import com.github.oliverschen.olirpc.annotaion.OliService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author ck
 */
public class OliRpcBeanNameGenerator extends AnnotationBeanNameGenerator {
    private static final Logger log = LoggerFactory.getLogger(OliRpcBeanNameGenerator.class);

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String name = obtainBeanName(definition);
        if (!ObjectUtils.isEmpty(name)) {
            return name;
        }
        return super.generateBeanName(definition,registry);
    }

    /**
     * beanName 的名字
     * @param definition BeanDefinition
     * @return name
     */
    private String obtainBeanName(BeanDefinition definition) {
        try {
            Class<?> aClass = Class.forName(definition.getBeanClassName());
            OliService oliService = aClass.getAnnotation(OliService.class);
            OliRefer oliRefer = aClass.getAnnotation(OliRefer.class);
            if (Objects.isNull(oliService) && Objects.isNull(oliRefer)) {
                log.debug("当前 bean 定义:{}没有自定义注解", definition.getBeanClassName());
                return null;
            }
            if (Objects.nonNull(oliService)) {
                log.debug("OliRpcBeanNameGenerator @OliService 注解");
                return oliService.name();
            }
            return oliRefer.name();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("OliRpcBeanNameGenerator error", e);
        }
        return null;
    }
}
