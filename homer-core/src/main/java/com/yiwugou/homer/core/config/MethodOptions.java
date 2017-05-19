package com.yiwugou.homer.core.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.loadbalance.LoadBalance;

import lombok.Data;

@Data
public class MethodOptions {

    /**
     * package.class.method.url 可用的Server
     */
    private List<Server> availableServers = new CopyOnWriteArrayList<>();

    /**
     * 不可用的server
     */
    private List<Server> unAvailableServers = new CopyOnWriteArrayList<>();

    /**
     * 重试次数
     */
    private Integer retry = RequestConfig.Default.RETRY;

    /**
     * 单秒中 单方法最大请求数
     */
    private Integer execute = RequestConfig.Default.EXECUTE;

    /**
     * 单方法并发数
     */
    private Integer active = RequestConfig.Default.ACTIVE;

    /**
     * 缓存时间 毫秒
     */
    private Long cache = RequestConfig.Default.CACHE;

    /**
     * 降级
     */
    private Boolean mock = RequestConfig.Default.MOCK;

    /**
     * 负载均衡
     */
    private LoadBalance loadBalance;

    private Integer connectTimeout = RequestConfig.Default.CONNECT_TIMEOUT;

    private Integer readTimeout = RequestConfig.Default.READ_TIMEOUT;
}
