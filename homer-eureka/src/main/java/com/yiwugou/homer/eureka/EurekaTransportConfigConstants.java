package com.yiwugou.homer.eureka;

/**
 *
 * TransportConfigConstants
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月11日 下午4:53:38
 */
final public class EurekaTransportConfigConstants {

    public static final String SESSION_RECONNECT_INTERVAL_KEY = "sessionedClientReconnectIntervalSeconds";
    public static final String QUARANTINE_REFRESH_PERCENTAGE_KEY = "retryableClientQuarantineRefreshPercentage";
    public static final String DATA_STALENESS_THRESHOLD_KEY = "applicationsResolverDataStalenessThresholdSeconds";
    public static final String APPLICATION_RESOLVER_USE_IP_KEY = "applicationsResolverUseIp";
    public static final String ASYNC_RESOLVER_REFRESH_INTERVAL_KEY = "asyncResolverRefreshIntervalMs";
    public static final String ASYNC_RESOLVER_WARMUP_TIMEOUT_KEY = "asyncResolverWarmupTimeoutMs";
    public static final String ASYNC_EXECUTOR_THREADPOOL_SIZE_KEY = "asyncExecutorThreadPoolSize";
    public static final String WRITE_CLUSTER_VIP_KEY = "writeClusterVip";
    public static final String READ_CLUSTER_VIP_KEY = "readClusterVip";
    public static final String BOOTSTRAP_RESOLVER_STRATEGY_KEY = "bootstrapResolverStrategy";
    public static final String USE_BOOTSTRAP_RESOLVER_FOR_QUERY = "useBootstrapResolverForQuery";

    public static final String TRANSPORT_CONFIG_SUB_NAMESPACE = "transport";

    public static class Values {
        public static final int SESSION_RECONNECT_INTERVAL = 20 * 60;
        public static final double QUARANTINE_REFRESH_PERCENTAGE = 0.66;
        public static final int DATA_STALENESS_TRHESHOLD = 5 * 60;
        public static final int ASYNC_RESOLVER_REFRESH_INTERVAL = 5 * 60 * 1000;
        public static final int ASYNC_RESOLVER_WARMUP_TIMEOUT = 5000;
        public static final int ASYNC_EXECUTOR_THREADPOOL_SIZE = 5;
    }
}