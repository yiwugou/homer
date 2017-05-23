package com.yiwugou.homer.eureka;

/**
 *
 * EurekaInstanceConfigConstants
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月11日 下午4:53:33
 */
public final class EurekaInstanceConfigConstants {
    // NOTE: all keys are before any prefixes are applied
    public static final String INSTANCE_ID_KEY = "instanceId";
    public static final String APP_NAME_KEY = "name";
    public static final String APP_GROUP_KEY = "appGroup";
    public static final String FALLBACK_APP_GROUP_KEY = "NETFLIX_APP_GROUP";
    public static final String ASG_NAME_KEY = "asgName";

    public static final String PORT_KEY = "port";
    public static final String SECURE_PORT_KEY = "securePort";
    public static final String PORT_ENABLED_KEY = PORT_KEY + ".enabled";
    public static final String SECURE_PORT_ENABLED_KEY = SECURE_PORT_KEY + ".enabled";

    public static final String VIRTUAL_HOSTNAME_KEY = "vipAddress";
    public static final String SECURE_VIRTUAL_HOSTNAME_KEY = "secureVipAddress";

    public static final String STATUS_PAGE_URL_PATH_KEY = "statusPageUrlPath";
    public static final String STATUS_PAGE_URL_KEY = "statusPageUrl";
    public static final String HOME_PAGE_URL_PATH_KEY = "homePageUrlPath";
    public static final String HOME_PAGE_URL_KEY = "homePageUrl";
    public static final String HEALTHCHECK_URL_PATH_KEY = "healthCheckUrlPath";
    public static final String HEALTHCHECK_URL_KEY = "healthCheckUrl";
    public static final String SECURE_HEALTHCHECK_URL_KEY = "secureHealthCheckUrl";

    public static final String LEASE_RENEWAL_INTERVAL_KEY = "lease.renewalInterval";
    public static final String LEASE_EXPIRATION_DURATION_KEY = "lease.duration";

    public static final String INSTANCE_METADATA_PREFIX = "metadata";

    public static final String DEFAULT_ADDRESS_RESOLUTION_ORDER_KEY = "defaultAddressResolutionOrder";
    public static final String TRAFFIC_ENABLED_ON_INIT_KEY = "traffic.enabled";

    public static class Values {
        public static final String UNKNOWN_APPLICATION = "unknown";

        public static final String DEFAULT_STATUSPAGE_URLPATH = "/Status";
        public static final String DEFAULT_HOMEPAGE_URLPATH = "/";
        public static final String DEFAULT_HEALTHCHECK_URLPATH = "/healthcheck";
    }
}
