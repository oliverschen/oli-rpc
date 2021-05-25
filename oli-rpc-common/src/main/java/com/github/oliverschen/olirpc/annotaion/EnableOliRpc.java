package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * 依赖 Spring 开启服务注解
 *
 * @author ck
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnableOliRpc {


}
