package com.yiwugou.homer.core.config;

public class NoneConfigLoader implements ConfigLoader {
    @Override
    public <T> T loader(String key, T defaultValue) {
        return defaultValue;
    }

}
