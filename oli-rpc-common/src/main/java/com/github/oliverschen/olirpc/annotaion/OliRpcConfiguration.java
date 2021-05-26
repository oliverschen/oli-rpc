package com.github.oliverschen.olirpc.annotaion;

import java.lang.annotation.*;

/**
 * @author ck
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OliRpcConfiguration {

}
