package com.yiwugou.homer.eureka;

import java.util.Map;

import com.netflix.appinfo.AbstractInstanceConfig;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.EurekaInstanceConfig;

public class HomerEurekaInstanceConfig extends AbstractInstanceConfig implements EurekaInstanceConfig {
    protected String namespace;

    protected DynamicProperty dynamicProperty;

    public HomerEurekaInstanceConfig(DynamicProperty dynamicProperty) {
        this(EurekaConstants.DEFAULT_CONFIG_NAMESPACE, dynamicProperty, new DataCenterInfo() {
            @Override
            public Name getName() {
                return Name.MyOwn;
            }
        });
    }

    public HomerEurekaInstanceConfig(String namespace, DynamicProperty dynamicProperty) {
        this(namespace, dynamicProperty, new DataCenterInfo() {
            @Override
            public Name getName() {
                return Name.MyOwn;
            }
        });
    }

    public HomerEurekaInstanceConfig(String namespace, DynamicProperty dynamicProperty, DataCenterInfo info) {
        super(info);
        this.namespace = namespace.endsWith(".") ? namespace : namespace + ".";
        this.dynamicProperty = dynamicProperty;
    }

    @Override
    public boolean isInstanceEnabledOnit() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaInstanceConfigConstants.TRAFFIC_ENABLED_ON_INIT_KEY,
                super.isInstanceEnabledOnit());
    }

    @Override
    public int getNonSecurePort() {
        return this.dynamicProperty.getIntProperty(this.namespace + EurekaInstanceConfigConstants.PORT_KEY,
                super.getNonSecurePort());
    }

    @Override
    public int getSecurePort() {
        return this.dynamicProperty.getIntProperty(this.namespace + EurekaInstanceConfigConstants.SECURE_PORT_KEY,
                super.getSecurePort());
    }

    @Override
    public boolean isNonSecurePortEnabled() {
        return this.dynamicProperty.getBooleanProperty(this.namespace + EurekaInstanceConfigConstants.PORT_ENABLED_KEY,
                super.isNonSecurePortEnabled());
    }

    @Override
    public boolean getSecurePortEnabled() {
        return this.dynamicProperty.getBooleanProperty(
                this.namespace + EurekaInstanceConfigConstants.SECURE_PORT_ENABLED_KEY, super.getSecurePortEnabled());
    }

    @Override
    public int getLeaseRenewalIntervalInSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaInstanceConfigConstants.LEASE_RENEWAL_INTERVAL_KEY,
                super.getLeaseRenewalIntervalInSeconds());
    }

    @Override
    public int getLeaseExpirationDurationInSeconds() {
        return this.dynamicProperty.getIntProperty(
                this.namespace + EurekaInstanceConfigConstants.LEASE_EXPIRATION_DURATION_KEY,
                super.getLeaseExpirationDurationInSeconds());
    }

    @Override
    public String getVirtualHostName() {
        if (this.isNonSecurePortEnabled()) {
            return this.dynamicProperty.getStringProperty(
                    this.namespace + EurekaInstanceConfigConstants.VIRTUAL_HOSTNAME_KEY, super.getVirtualHostName());
        } else {
            return null;
        }
    }

    @Override
    public String getSecureVirtualHostName() {
        if (this.getSecurePortEnabled()) {
            return this.dynamicProperty.getStringProperty(
                    this.namespace + EurekaInstanceConfigConstants.SECURE_VIRTUAL_HOSTNAME_KEY,
                    super.getSecureVirtualHostName());
        } else {
            return null;
        }
    }

    @Override
    public String getASGName() {
        return this.dynamicProperty.getStringProperty(this.namespace + EurekaInstanceConfigConstants.ASG_NAME_KEY,
                super.getASGName());
    }

    /**
     * Gets the metadata map associated with the instance. The properties that
     * will be looked up for this will be <code>namespace + ".metadata"</code>.
     *
     * <p>
     * For instance, if the given namespace is <code>eureka.appinfo</code>, the
     * metadata keys are searched under the namespace
     * <code>eureka.appinfo.metadata</code>.
     * </p>
     */
    @Override
    public Map<String, String> getMetadataMap() {
        String metadataNamespace = this.namespace + EurekaInstanceConfigConstants.INSTANCE_METADATA_PREFIX + ".";
        String subsetPrefix = metadataNamespace.charAt(metadataNamespace.length() - 1) == '.'
                ? metadataNamespace.substring(0, metadataNamespace.length() - 1) : metadataNamespace;

        return this.dynamicProperty.getMapProperty(subsetPrefix);

    }

    @Override
    public String getInstanceId() {
        String result = this.dynamicProperty
                .getStringProperty(this.namespace + EurekaInstanceConfigConstants.INSTANCE_ID_KEY, null);
        return result == null ? null : result.trim();
    }

    @Override
    public String getAppname() {
        return this.dynamicProperty.getStringProperty(this.namespace + EurekaInstanceConfigConstants.APP_NAME_KEY,
                EurekaInstanceConfigConstants.Values.UNKNOWN_APPLICATION).trim();
    }

    @Override
    public String getAppGroupName() {
        String appGrpNameFromEnv = System.getProperty(EurekaInstanceConfigConstants.FALLBACK_APP_GROUP_KEY,
                EurekaInstanceConfigConstants.Values.UNKNOWN_APPLICATION);
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaInstanceConfigConstants.APP_GROUP_KEY, appGrpNameFromEnv)
                .trim();
    }

    @Override
    public String getIpAddress() {
        return super.getIpAddress();
    }

    @Override
    public String getStatusPageUrlPath() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaInstanceConfigConstants.STATUS_PAGE_URL_PATH_KEY,
                EurekaInstanceConfigConstants.Values.DEFAULT_STATUSPAGE_URLPATH);
    }

    @Override
    public String getStatusPageUrl() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaInstanceConfigConstants.STATUS_PAGE_URL_KEY, null);
    }

    @Override
    public String getHomePageUrlPath() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaInstanceConfigConstants.HOME_PAGE_URL_PATH_KEY,
                EurekaInstanceConfigConstants.Values.DEFAULT_HOMEPAGE_URLPATH);
    }

    @Override
    public String getHomePageUrl() {
        return this.dynamicProperty.getStringProperty(this.namespace + EurekaInstanceConfigConstants.HOME_PAGE_URL_KEY,
                null);
    }

    @Override
    public String getHealthCheckUrlPath() {
        return this.dynamicProperty.getStringProperty(
                this.namespace + EurekaInstanceConfigConstants.HEALTHCHECK_URL_PATH_KEY,
                EurekaInstanceConfigConstants.Values.DEFAULT_HEALTHCHECK_URLPATH);
    }

    @Override
    public String getHealthCheckUrl() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaInstanceConfigConstants.HEALTHCHECK_URL_KEY, null);
    }

    @Override
    public String getSecureHealthCheckUrl() {
        return this.dynamicProperty
                .getStringProperty(this.namespace + EurekaInstanceConfigConstants.SECURE_HEALTHCHECK_URL_KEY, null);
    }

    @Override
    public String[] getDefaultAddressResolutionOrder() {
        String result = this.dynamicProperty.getStringProperty(
                this.namespace + EurekaInstanceConfigConstants.DEFAULT_ADDRESS_RESOLUTION_ORDER_KEY, null);
        return result == null ? new String[0] : result.split(",");
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }
}
