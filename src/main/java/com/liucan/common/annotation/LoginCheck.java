package com.liucan.common.annotation;

import java.lang.annotation.*;

/**
 * @author liucan
 * @version 18-12-8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginCheck {
    boolean required() default true;
}
