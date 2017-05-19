package com.yiwugou.homer.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yiwugou.homer.core.enums.MethodEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    MethodEnum method() default MethodEnum.GET;

    String value() default "";
}
