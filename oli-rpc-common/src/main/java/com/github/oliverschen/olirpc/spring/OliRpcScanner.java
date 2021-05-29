package com.github.oliverschen.olirpc.spring;

import com.github.oliverschen.olirpc.annotaion.OliRefer;
import com.github.oliverschen.olirpc.annotaion.OliService;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 类路径 bean 定义扫描器
 * @author ck
 */
public class OliRpcScanner extends ClassPathBeanDefinitionScanner {

    public OliRpcScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        // 自定义注解标记的类才能被扫描到
        addIncludeFilter(new AnnotationTypeFilter(OliService.class));
        addIncludeFilter(new AnnotationTypeFilter(OliRefer.class));
        return super.doScan(basePackages);
    }
}
