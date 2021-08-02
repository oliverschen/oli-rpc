package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * @author ck
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OliService {

    String name() default "" ;
}
