package com.yiwugou.homer.eureka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.netflix.appinfo.EurekaAccept;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import com.yiwugou.homer.eureka.EurekaClientConfigConstants.Values;

import lombok.Data;

@Data
public class HomerEurekaClientConfig implements EurekaClientConfig {
    public static final String DEFAULT_ZONE = "defaultZone";

    private final String namespace;
    private final DynamicProperty dynamicProperty;
    private final EurekaTransportConfig transportConfig;

    public HomerEurekaClientConfig(String namespace, DynamicProperty dynamicProperty) {
        this.namespace = namespace.endsWith(".") ? namespace : namespace + ".";
        this.dynamicProperty = dynamicProperty;
        this.transportConfig = new HomerEurekaTransportConfig(namespace, this.dynamicProperty);
    }

    @Override
    public int getRegistryFetchIntervalSeconds() {
        return this.dynamicProperty
                .getIntProperty(this.namespace + EurekaClientConfigConstants.REGISTRY_REFRESH_INTERVAL_KEY, 30);
    }

    @Override
    public int getInstanceInfoReplicationIntervalSeconds() {
        return this.dynamicProperty
                .getIntProperty(this.namespace + EurekaClientConfigConstants.REGISTRATION_REPLICATION_INTERVAL_KEY, 30);
    }

    @Override
    public int getInitialInstanceInfoReplicationIntervalSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.INITIAL_REGISTRATION_REPLICATION_DELAY_KEY, 40);
    }

    @Override
    public int getEurekaServiceUrlPollIntervalSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_URL_POLL_INTERVAL_KEY, 5 * 60 * 1000) / 1000;
    }

    @Override
    public String getProxyHost() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_PROXY_HOST_KEY, null);
    }

    @Override
    public String getProxyPort() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_PROXY_PORT_KEY, null);
    }

    @Override
    public String getProxyUserName() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_PROXY_USERNAME_KEY, null);
    }

    @Override
    public String getProxyPassword() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_PROXY_PASSWORD_KEY, null);
    }

    @Override
    public boolean shouldGZipContent() {
        return this.dynamicProperty
                .getBooleanProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_GZIP_CONTENT_KEY, true);
    }

    @Override
    public int getEurekaServerReadTimeoutSeconds() {
        return this.dynamicProperty
                .getIntProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_READ_TIMEOUT_KEY, 8);
    }

    @Override
    public int getEurekaServerConnectTimeoutSeconds() {
        return this.dynamicProperty
                .getIntProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_CONNECT_TIMEOUT_KEY, 5);
    }

    @Override
    public String getBackupRegistryImpl() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.BACKUP_REGISTRY_CLASSNAME_KEY, null);
    }

    @Override
    public int getEurekaServerTotalConnections() {
        return this.dynamicProperty
                .getIntProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_MAX_CONNECTIONS_KEY, 200);
    }

    @Override
    public int getEurekaServerTotalConnectionsPerHost() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_MAX_CONNECTIONS_PER_HOST_KEY, 50);
    }

    @Override
    public String getEurekaServerURLContext() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_URL_CONTEXT_KEY,
                        this.dynamicProperty.getStringProperty(
                                this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_FALLBACK_URL_CONTEXT_KEY,
                                null));
    }

    @Override
    public String getEurekaServerPort() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_PORT_KEY,
                this.dynamicProperty.getStringProperty(
                        this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_FALLBACK_PORT_KEY, null));
    }

    @Override
    public String getEurekaServerDNSName() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_DNS_NAME_KEY,
                        this.dynamicProperty.getStringProperty(
                                this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_FALLBACK_DNS_NAME_KEY,
                                null));
    }

    @Override
    public boolean shouldUseDnsForFetchingServiceUrls() {
        return this.dynamicProperty.getBooleanProperty(this.namespace + EurekaClientConfigConstants.SHOULD_USE_DNS_KEY,
                false);
    }

    @Override
    public boolean shouldRegisterWithEureka() {
        return this.dynamicProperty
                .getBooleanProperty(this.namespace + EurekaClientConfigConstants.REGISTRATION_ENABLED_KEY, true);
    }

    @Override
    public boolean shouldPreferSameZoneEureka() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaClientConfigConstants.SHOULD_PREFER_SAME_ZONE_SERVER_KEY, true);
    }

    @Override
    public boolean allowRedirects() {
        return this.dynamicProperty
                .getBooleanProperty(this.namespace + EurekaClientConfigConstants.SHOULD_ALLOW_REDIRECTS_KEY, false);
    }

    @Override
    public boolean shouldLogDeltaDiff() {
        return this.dynamicProperty
                .getBooleanProperty(this.namespace + EurekaClientConfigConstants.SHOULD_LOG_DELTA_DIFF_KEY, false);
    }

    @Override
    public boolean shouldDisableDelta() {
        return this.dynamicProperty
                .getBooleanProperty(this.namespace + EurekaClientConfigConstants.SHOULD_DISABLE_DELTA_KEY, false);
    }

    @Override
    public String fetchRegistryForRemoteRegions() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.SHOULD_FETCH_REMOTE_REGION_KEY, null);
    }

    @Override
    public String getRegion() {
        String defaultEurekaRegion = this.dynamicProperty.getStringProperty(
                EurekaClientConfigConstants.CLIENT_REGION_FALLBACK_KEY, Values.DEFAULT_CLIENT_REGION);
        return this.dynamicProperty.getStringProperty(this.namespace + EurekaClientConfigConstants.CLIENT_REGION_KEY,
                defaultEurekaRegion);
    }

    @Override
    public String[] getAvailabilityZones(String region) {
        return this.dynamicProperty.getStringProperty(
                this.namespace + region + "." + EurekaClientConfigConstants.CONFIG_AVAILABILITY_ZONE_PREFIX,
                DEFAULT_ZONE).split(",");
    }

    @Override
    public List<String> getEurekaServerServiceUrls(String myZone) {
        String serviceUrls = this.dynamicProperty.getStringProperty(
                this.namespace + EurekaClientConfigConstants.CONFIG_EUREKA_SERVER_SERVICE_URL_PREFIX + "." + myZone,
                null);
        if (serviceUrls == null || serviceUrls.isEmpty()) {
            serviceUrls = this.dynamicProperty.getStringProperty(
                    this.namespace + EurekaClientConfigConstants.CONFIG_EUREKA_SERVER_SERVICE_URL_PREFIX + ".default",
                    null);

        }
        if (serviceUrls != null) {
            return Arrays.asList(serviceUrls.split(","));
        }

        return new ArrayList<>();
    }

    @Override
    public boolean shouldFilterOnlyUpInstances() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaClientConfigConstants.SHOULD_FILTER_ONLY_UP_INSTANCES_KEY, true);
    }

    @Override
    public int getEurekaConnectionIdleTimeoutSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.EUREKA_SERVER_CONNECTION_IDLE_TIMEOUT_KEY, 30);
    }

    @Override
    public boolean shouldFetchRegistry() {
        return this.dynamicProperty
                .getBooleanProperty(this.namespace + EurekaClientConfigConstants.FETCH_REGISTRY_ENABLED_KEY, true);
    }

    @Override
    public String getRegistryRefreshSingleVipAddress() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.FETCH_SINGLE_VIP_ONLY_KEY, null);
    }

    @Override
    public int getHeartbeatExecutorThreadPoolSize() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.HEARTBEAT_THREADPOOL_SIZE_KEY,
                EurekaClientConfigConstants.Values.DEFAULT_EXECUTOR_THREAD_POOL_SIZE);
    }

    @Override
    public int getHeartbeatExecutorExponentialBackOffBound() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.HEARTBEAT_BACKOFF_BOUND_KEY,
                EurekaClientConfigConstants.Values.DEFAULT_EXECUTOR_THREAD_POOL_BACKOFF_BOUND);
    }

    @Override
    public int getCacheRefreshExecutorThreadPoolSize() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.CACHEREFRESH_THREADPOOL_SIZE_KEY,
                EurekaClientConfigConstants.Values.DEFAULT_EXECUTOR_THREAD_POOL_SIZE);
    }

    @Override
    public int getCacheRefreshExecutorExponentialBackOffBound() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaClientConfigConstants.CACHEREFRESH_BACKOFF_BOUND_KEY,
                EurekaClientConfigConstants.Values.DEFAULT_EXECUTOR_THREAD_POOL_BACKOFF_BOUND);
    }

    @Override
    public String getDollarReplacement() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaClientConfigConstants.CONFIG_DOLLAR_REPLACEMENT_KEY,
                Values.CONFIG_DOLLAR_REPLACEMENT);
    }

    @Override
    public String getEscapeCharReplacement() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaClientConfigConstants.CONFIG_ESCAPE_CHAR_REPLACEMENT_KEY,
                Values.CONFIG_ESCAPE_CHAR_REPLACEMENT);
    }

    @Override
    public boolean shouldOnDemandUpdateStatusChange() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaClientConfigConstants.SHOULD_ONDEMAND_UPDATE_STATUS_KEY, true);
    }

    @Override
    public String getEncoderName() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.CLIENT_ENCODER_NAME_KEY, null);
    }

    @Override
    public String getDecoderName() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaClientConfigConstants.CLIENT_DECODER_NAME_KEY, null);
    }

    @Override
    public String getClientDataAccept() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaClientConfigConstants.CLIENT_DATA_ACCEPT_KEY, EurekaAccept.full.name());
    }

    @Override
    public String getExperimental(String name) {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaClientConfigConstants.CONFIG_EXPERIMENTAL_PREFIX + "." + name, null);
    }

    @Override
    public EurekaTransportConfig getTransportConfig() {
        return this.transportConfig;
    }
}
