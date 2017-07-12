package com.yiwugou.homer.core.config;

import com.yiwugou.homer.core.constant.RequestDefault;
import com.yiwugou.homer.core.loadbalance.LoadBalance;
import com.yiwugou.homer.core.server.ServerHandler;

import lombok.Data;

/**
 *
 * MethodMetadata
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:10:10
 */
@Data
public class MethodMetadata {
    private ServerHandler serverHandler;
    /**
     * 重试次数
     */
    private Integer retry = RequestDefault.RETRY;

    /**
     * 单秒中 单方法最大请求数
     */
    private Integer execute = RequestDefault.EXECUTE;

    /**
     * 单方法并发数
     */
    private Integer active = RequestDefault.ACTIVE;

    /**
     * 缓存时间 毫秒
     */
    private Long cache = RequestDefault.CACHE;

    /**
     * 降级
     */
    private Boolean mock = RequestDefault.MOCK;

    /**
     * 负载均衡
     */
    private LoadBalance loadBalance;

    /**
     * 毫秒
     */
    private Integer connectTimeout = RequestDefault.CONNECT_TIMEOUT;

    /**
     * 毫秒
     */
    private Integer readTimeout = RequestDefault.READ_TIMEOUT;
}
