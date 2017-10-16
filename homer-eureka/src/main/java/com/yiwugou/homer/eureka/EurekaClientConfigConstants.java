package com.yiwugou.homer.eureka;

/**
 *
 * EurekaConfigConstants
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月11日 下午4:53:27
 */
final public class EurekaClientConfigConstants {
    public static final String CLIENT_REGION_FALLBACK_KEY = "eureka.region";

    public static final String CLIENT_REGION_KEY = "region";

    public static final String REGISTRATION_ENABLED_KEY = "registration.enabled";
    public static final String FETCH_REGISTRY_ENABLED_KEY = "shouldFetchRegistry";

    public static final String REGISTRY_REFRESH_INTERVAL_KEY = "client.refresh.interval";
    public static final String REGISTRATION_REPLICATION_INTERVAL_KEY = "appinfo.replicate.interval";
    public static final String INITIAL_REGISTRATION_REPLICATION_DELAY_KEY = "appinfo.initial.replicate.time";
    public static final String HEARTBEAT_THREADPOOL_SIZE_KEY = "client.heartbeat.threadPoolSize";
    public static final String HEARTBEAT_BACKOFF_BOUND_KEY = "client.heartbeat.exponentialBackOffBound";
    public static final String CACHEREFRESH_THREADPOOL_SIZE_KEY = "client.cacheRefresh.threadPoolSize";
    public static final String CACHEREFRESH_BACKOFF_BOUND_KEY = "client.cacheRefresh.exponentialBackOffBound";

    public static final String SHOULD_ONDEMAND_UPDATE_STATUS_KEY = "shouldOnDemandUpdateStatusChange";
    public static final String SHOULD_DISABLE_DELTA_KEY = "disableDelta";
    public static final String SHOULD_FETCH_REMOTE_REGION_KEY = "fetchRemoteRegionsRegistry";
    public static final String SHOULD_FILTER_ONLY_UP_INSTANCES_KEY = "shouldFilterOnlyUpInstances";
    public static final String FETCH_SINGLE_VIP_ONLY_KEY = "registryRefreshSingleVipAddress";
    public static final String CLIENT_ENCODER_NAME_KEY = "encoderName";
    public static final String CLIENT_DECODER_NAME_KEY = "decoderName";
    public static final String CLIENT_DATA_ACCEPT_KEY = "clientDataAccept";

    public static final String BACKUP_REGISTRY_CLASSNAME_KEY = "backupregistry";

    public static final String SHOULD_PREFER_SAME_ZONE_SERVER_KEY = "preferSameZone";
    public static final String SHOULD_ALLOW_REDIRECTS_KEY = "allowRedirects";
    public static final String SHOULD_USE_DNS_KEY = "shouldUseDns";

    public static final String EUREKA_SERVER_URL_POLL_INTERVAL_KEY = "serviceUrlPollIntervalMs";
    public static final String EUREKA_SERVER_URL_CONTEXT_KEY = "eurekaServer.context";
    public static final String EUREKA_SERVER_FALLBACK_URL_CONTEXT_KEY = "context";
    public static final String EUREKA_SERVER_PORT_KEY = "eurekaServer.port";
    public static final String EUREKA_SERVER_FALLBACK_PORT_KEY = "port";
    public static final String EUREKA_SERVER_DNS_NAME_KEY = "eurekaServer.domainName";
    public static final String EUREKA_SERVER_FALLBACK_DNS_NAME_KEY = "domainName";

    public static final String EUREKA_SERVER_PROXY_HOST_KEY = "eurekaServer.proxyHost";
    public static final String EUREKA_SERVER_PROXY_PORT_KEY = "eurekaServer.proxyPort";
    public static final String EUREKA_SERVER_PROXY_USERNAME_KEY = "eurekaServer.proxyUserName";
    public static final String EUREKA_SERVER_PROXY_PASSWORD_KEY = "eurekaServer.proxyPassword";

    public static final String EUREKA_SERVER_GZIP_CONTENT_KEY = "eurekaServer.gzipContent";
    public static final String EUREKA_SERVER_READ_TIMEOUT_KEY = "eurekaServer.readTimeout";
    public static final String EUREKA_SERVER_CONNECT_TIMEOUT_KEY = "eurekaServer.connectTimeout";
    public static final String EUREKA_SERVER_MAX_CONNECTIONS_KEY = "eurekaServer.maxTotalConnections";
    public static final String EUREKA_SERVER_MAX_CONNECTIONS_PER_HOST_KEY = "eurekaServer.maxConnectionsPerHost";

    public static final String EUREKA_SERVER_CONNECTION_IDLE_TIMEOUT_KEY = "eurekaserver.connectionIdleTimeoutInSeconds";

    public static final String SHOULD_LOG_DELTA_DIFF_KEY = "printDeltaFullDiff";

    public static final String CONFIG_DOLLAR_REPLACEMENT_KEY = "dollarReplacement";
    public static final String CONFIG_ESCAPE_CHAR_REPLACEMENT_KEY = "escapeCharReplacement";

    public static final String CONFIG_EXPERIMENTAL_PREFIX = "experimental";
    public static final String CONFIG_AVAILABILITY_ZONE_PREFIX = "availabilityZones";
    public static final String CONFIG_EUREKA_SERVER_SERVICE_URL_PREFIX = "serviceUrl";

    public static class Values {
        public static final String CONFIG_DOLLAR_REPLACEMENT = "_-";
        public static final String CONFIG_ESCAPE_CHAR_REPLACEMENT = "__";

        public static final String DEFAULT_CLIENT_REGION = "us-east-1";

        public static final int DEFAULT_EXECUTOR_THREAD_POOL_SIZE = 5;
        public static final int DEFAULT_EXECUTOR_THREAD_POOL_BACKOFF_BOUND = 10;
    }
}
