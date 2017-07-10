package com.yiwugou.homer.core.config;

import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

import lombok.AllArgsConstructor;

/**
 *
 * PropertiesConfigLoader
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:10:19
 */
@AllArgsConstructor
public class PropertiesConfigLoader implements ConfigLoader {

    private Properties properties;

    @Override
    public <T> T loader(String key, T defaultValue) {
        if (key.startsWith("${") && key.endsWith("}")) {
            key = key.substring(2, key.length() - 1);
        }
        String value = this.properties.getProperty(key, defaultValue == null ? null : defaultValue.toString());
        return value == null ? null : (T) CommonUtils.stringToBasic(value, defaultValue.getClass());
    }

}
