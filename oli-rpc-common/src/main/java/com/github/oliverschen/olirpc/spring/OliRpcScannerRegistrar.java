package com.github.oliverschen.olirpc.spring;

import com.github.oliverschen.olirpc.annotaion.EnableOliRpc;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.util.Objects;
import java.util.Optional;

import static com.github.oliverschen.olirpc.constant.Constants.BASE_PACKAGE;

/**
 * ResourceLoaderAware：一般 Aware 结尾的都是注入 Spring 中资源，这里注入 ResourceLoader 资源
 *
 * @author ck
 */
public class OliRpcScannerRegistrar implements ResourceLoaderAware, ImportBeanDefinitionRegistrar {

    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator beanNameGenerator) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(EnableOliRpc.class.getName()));
        // 获取 oliRpcScan 配置的基础包路径
        String[] packages = Optional.ofNullable(attributes).orElseGet(AnnotationAttributes::new).getStringArray(BASE_PACKAGE);
        if (packages.length == 0) {
            packages = new String[]{((StandardAnnotationMetadata) metadata).getIntrospectedClass().getPackage().getName()};
        }
        // 指定扫描器
        OliRpcScanner scanner = new OliRpcScanner(registry);
        if (Objects.nonNull(resourceLoader)) {
            scanner.setResourceLoader(resourceLoader);
        }
        scanner.setBeanNameGenerator(new OliRpcBeanNameGenerator());
        scanner.doScan(packages);
    }
}
