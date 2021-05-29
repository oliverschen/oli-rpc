package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * @author ck
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OliRefer {

    String name() default "";

}
