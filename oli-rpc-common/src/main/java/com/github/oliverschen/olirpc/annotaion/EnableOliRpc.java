package com.github.oliverschen.olirpc.annotaion;

import com.github.oliverschen.olirpc.spring.OliRpcScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ck
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(OliRpcScannerRegistrar.class)
public @interface EnableOliRpc {

    /**
     * 要扫描的包路径
     */
    String[] basePackage() default {};
}
