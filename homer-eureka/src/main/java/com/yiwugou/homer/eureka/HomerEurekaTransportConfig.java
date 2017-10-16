package com.yiwugou.homer.eureka;

import com.netflix.discovery.shared.transport.EurekaTransportConfig;

/**
 *
 * HomerEurekaTransportConfig
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:20:48
 */
public class HomerEurekaTransportConfig implements EurekaTransportConfig {

    private static final String SUB_NAMESPACE = EurekaTransportConfigConstants.TRANSPORT_CONFIG_SUB_NAMESPACE + ".";

    private final String namespace;
    private final DynamicProperty dynamicProperty;

    public HomerEurekaTransportConfig(String parentNamespace, DynamicProperty dynamicProperty) {
        this.namespace = parentNamespace == null ? SUB_NAMESPACE
                : (parentNamespace.endsWith(".") ? parentNamespace + SUB_NAMESPACE
                        : parentNamespace + "." + SUB_NAMESPACE);
        this.dynamicProperty = dynamicProperty;
    }

    @Override
    public int getSessionedClientReconnectIntervalSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaTransportConfigConstants.SESSION_RECONNECT_INTERVAL_KEY,
                EurekaTransportConfigConstants.Values.SESSION_RECONNECT_INTERVAL);
    }

    @Override
    public double getRetryableClientQuarantineRefreshPercentage() {
        return this.dynamicProperty.getDoubleProperty(
                this.namespace + EurekaTransportConfigConstants.QUARANTINE_REFRESH_PERCENTAGE_KEY,
                EurekaTransportConfigConstants.Values.QUARANTINE_REFRESH_PERCENTAGE);
    }

    @Override
    public int getApplicationsResolverDataStalenessThresholdSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaTransportConfigConstants.DATA_STALENESS_THRESHOLD_KEY,
                EurekaTransportConfigConstants.Values.DATA_STALENESS_TRHESHOLD);
    }

    @Override
    public boolean applicationsResolverUseIp() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaTransportConfigConstants.APPLICATION_RESOLVER_USE_IP_KEY, false);
    }

    @Override
    public int getAsyncResolverRefreshIntervalMs() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaTransportConfigConstants.ASYNC_RESOLVER_REFRESH_INTERVAL_KEY,
                EurekaTransportConfigConstants.Values.ASYNC_RESOLVER_REFRESH_INTERVAL);
    }

    @Override
    public int getAsyncResolverWarmUpTimeoutMs() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaTransportConfigConstants.ASYNC_RESOLVER_WARMUP_TIMEOUT_KEY,
                EurekaTransportConfigConstants.Values.ASYNC_RESOLVER_WARMUP_TIMEOUT);
    }

    @Override
    public int getAsyncExecutorThreadPoolSize() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaTransportConfigConstants.ASYNC_EXECUTOR_THREADPOOL_SIZE_KEY,
                EurekaTransportConfigConstants.Values.ASYNC_EXECUTOR_THREADPOOL_SIZE);
    }

    @Override
    public String getWriteClusterVip() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaTransportConfigConstants.WRITE_CLUSTER_VIP_KEY, null);
    }

    @Override
    public String getReadClusterVip() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaTransportConfigConstants.READ_CLUSTER_VIP_KEY, null);
    }

    @Override
    public String getBootstrapResolverStrategy() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaTransportConfigConstants.BOOTSTRAP_RESOLVER_STRATEGY_KEY, null);
    }

    @Override
    public boolean useBootstrapResolverForQuery() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaTransportConfigConstants.USE_BOOTSTRAP_RESOLVER_FOR_QUERY, true);
    }
}
