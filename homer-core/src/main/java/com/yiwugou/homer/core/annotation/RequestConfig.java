package com.yiwugou.homer.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yiwugou.homer.core.filter.Filter;
import com.yiwugou.homer.core.interceptor.Interceptor;
import com.yiwugou.homer.core.loadbalance.LoadBalance;
import com.yiwugou.homer.core.loadbalance.RandomLoadBalance;

/**
 *
 * RequestConfig
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:09:00
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestConfig {
    /**
     * 重试次数
     */
    int retry() default -1;

    /**
     * 单秒中 单方法最大请求数
     */
    int execute() default -1;

    /**
     * 单方法并发数
     */
    int active() default -1;

    /**
     * 缓存时间 毫秒
     */
    long cache() default -1;

    /**
     * <pre>
     * 降级 默认  true  代表失败返回 null
     * 如果不忽略异常 设置为 false
     * </pre>
     */
    boolean mock() default true;

    /**
     * 连接时间 毫秒
     */
    int connectTimeout() default -1;

    /**
     * 读取时间 毫秒
     */
    int readTimeout() default -1;

    /**
     * 负载均衡
     */
    Class<? extends LoadBalance> loadBalance() default RandomLoadBalance.class;

    /**
     * 过滤器
     */
    Class<? extends Filter>[] filters() default {};

    /**
     * 拦截器
     */
    Class<? extends Interceptor>[] interceptors() default {};
}
