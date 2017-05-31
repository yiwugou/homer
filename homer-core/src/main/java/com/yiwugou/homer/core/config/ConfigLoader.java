package com.yiwugou.homer.core.config;

public interface ConfigLoader {
    /**
     * <pre>
     * key: package.Class.{conf} or package.Class.method.{conf}
     * </pre>
     */
    <T> T loader(String key, T defaultValue);

    static final String URL = ".url";
    static final String EUREKA_SERVICE_ID = ".eurekaServiceId";
    static final String RETRY = ".retry";
    static final String EXECUTE = ".execute";
    static final String MOCK = ".mock";
    static final String ACTIVE = ".active";
    static final String CACHE = ".cache";
    static final String CONNECT_TIMEOUT = ".connectTimeout";
    static final String READ_TIMEOUT = ".readTimeout";
    static final String LOAD_BALANCE = ".loadbalance";
}
