package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * 自适性组件注解
 *
 * @author ck
 */
@Inherited
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OliAdapt {

    String value() default "";
}
