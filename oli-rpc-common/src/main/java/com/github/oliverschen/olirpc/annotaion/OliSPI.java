package com.github.oliverschen.olirpc.annotaion;

/**
 * 参考 dubbo @SPI
 * 扩展点注解
 * @author ck
 */
public @interface OliSPI {

    /**
     * default value
     */
    String value() default "";
}
