package com.github.oliverschen.olirpc.annotaion;

/**
 * 选择器，参考 dubbo @Adaptive 注解功能
 * 选择当前要执行的组件
 * @author ck
 */
public @interface OliSelect {

    /**
     * defualt value
     * @return
     */
    String value() default "";
}
