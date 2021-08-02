package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * 选择器，参考 dubbo @Adaptive 注解功能
 * 选择当前要执行的组件
 * @author ck
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OliSelect {

    /**
     * defualt value
     * @return
     */
    String value() default "";
}
