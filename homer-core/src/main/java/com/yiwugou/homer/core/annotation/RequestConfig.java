package com.yiwugou.homer.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yiwugou.homer.core.constant.RequestDefault;
import com.yiwugou.homer.core.enums.LoadBalanceEnum;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestConfig {
    /**
     * 重试次数
     */
    int retry() default RequestDefault.RETRY;

    /**
     * 单秒中 单方法最大请求数
     */
    int execute() default RequestDefault.EXECUTE;

    /**
     * 单方法并发数
     */
    int active() default RequestDefault.ACTIVE;

    /**
     * 缓存时间 毫秒
     */
    long cache() default RequestDefault.CACHE;

    /**
     * 降级
     */
    boolean mock() default RequestDefault.MOCK;

    int connectTimeout() default RequestDefault.CONNECT_TIMEOUT;

    int readTimeout() default RequestDefault.READ_TIMEOUT;

    /**
     * 负载均衡
     */
    LoadBalanceEnum loadBalance() default LoadBalanceEnum.RANDOM;
}
