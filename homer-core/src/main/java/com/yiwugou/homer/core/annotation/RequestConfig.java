package com.yiwugou.homer.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yiwugou.homer.core.enums.LoadBalanceEnum;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestConfig {
    /**
     * 重试次数
     */
    int retry() default Default.RETRY;

    /**
     * 单秒中 单方法最大请求数
     */
    int execute() default Default.EXECUTE;

    /**
     * 单方法并发数
     */
    int active() default Default.ACTIVE;

    /**
     * 缓存时间 毫秒
     */
    long cache() default Default.CACHE;

    /**
     * 降级
     */
    boolean mock() default Default.MOCK;

    int connectTimeout() default Default.CONNECT_TIMEOUT;

    int readTimeout() default Default.READ_TIMEOUT;

    /**
     * 负载均衡
     */
    LoadBalanceEnum loadBalance() default LoadBalanceEnum.RANDOM;

    static class Default {
        public static final int RETRY = 0;
        public static final int EXECUTE = 1000;
        public static final int ACTIVE = 100;
        public static final long CACHE = 0;
        public static final boolean MOCK = true;
        public static final int CONNECT_TIMEOUT = 1000;
        public static final int READ_TIMEOUT = 3000;
        public static final String LOAD_BALANCE = LoadBalanceEnum.RANDOM.toString();
    }
}
