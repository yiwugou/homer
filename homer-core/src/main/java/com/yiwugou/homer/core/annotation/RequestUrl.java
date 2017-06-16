package com.yiwugou.homer.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestUrl {

    String[] value() default "";

    /**
     * username:password
     */
    String basicAuth() default "";

    /**
     * 降级 必须为注解所在接口的一个默认实现类
     */
    Class<?> fallback() default void.class;
}
