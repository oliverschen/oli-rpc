package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * 参考 dubbo @SPI
 * 扩展点注解
 * @author ck
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OliSPI {

    /**
     * default value
     * 默认生效的组件，eg: loadBalance 默认生效 random，注解默认值就标注 random
     */
    String value() default "";
}
