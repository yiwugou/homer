package com.yiwugou.homer.core.config;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.constant.RequestDefault;
import com.yiwugou.homer.core.loadbalance.LoadBalance;

import lombok.Data;

@Data
public class MethodOptions {

    /**
     * package.class.method.url 可用的Server
     */
    private List<Server> upServers = new CopyOnWriteArrayList<>();

    /**
     * 不可用的server
     */
    private List<Server> downServers = new CopyOnWriteArrayList<>();

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

    private Integer connectTimeout = RequestDefault.CONNECT_TIMEOUT;

    private Integer readTimeout = RequestDefault.READ_TIMEOUT;
}
