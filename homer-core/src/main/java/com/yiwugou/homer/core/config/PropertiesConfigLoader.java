package com.yiwugou.homer.core.config;

import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class PropertiesConfigLoader implements ConfigLoader {

    @Setter(AccessLevel.PROTECTED)
    private Properties properties;

    @Override
    public <T> T loader(String key, T defaultValue) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        String value = this.properties.getProperty(key, defaultValue == null ? "" : defaultValue.toString());
        return (T) CommonUtils.stringToBasic(value, defaultValue.getClass());
    }

}