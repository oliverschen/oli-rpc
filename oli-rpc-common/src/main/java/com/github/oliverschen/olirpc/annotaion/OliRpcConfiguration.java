package com.github.oliverschen.olirpc.annotaion;

import com.github.oliverschen.olirpc.spring.OliRpcScannerConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ck
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(OliRpcScannerConfigurer.class)
public @interface OliRpcConfiguration {

}
