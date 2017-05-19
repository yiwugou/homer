package com.yiwugou.homer.core.config;

public interface ConfigLoader {
    <T> T loader(String key, T defaultValue);

    static final String URL = ".url";
    static final String RETRY = ".retry";
    static final String EXECUTE = ".execute";
    static final String MOCK = ".mock";
    static final String ACTIVE = ".active";
    static final String CACHE = ".cache";
    static final String LOAD_BALANCE = ".loadbalance";
}
