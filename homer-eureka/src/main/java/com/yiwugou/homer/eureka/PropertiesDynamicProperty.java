package com.yiwugou.homer.eureka;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PropertiesDynamicProperty implements DynamicProperty {

    private Properties properties;

    @Override
    public int getIntProperty(String key, int def) {
        String value = this.properties.getProperty(key);
        if (CommonUtils.hasTest(value)) {
            return Integer.parseInt(value);
        }
        return def;
    }

    @Override
    public String getStringProperty(String key, String def) {
        String value = this.properties.getProperty(key);
        if (CommonUtils.hasTest(value)) {
            return value;
        }
        return def;
    }

    @Override
    public boolean getBooleanProperty(String key, boolean def) {
        String value = this.properties.getProperty(key);
        if (CommonUtils.hasTest(value)) {
            return Boolean.parseBoolean(value);
        }
        return def;
    }

    @Override
    public double getDoubleProperty(String key, double def) {
        String value = this.properties.getProperty(key);
        if (CommonUtils.hasTest(value)) {
            return Double.parseDouble(value);
        }
        return def;
    }

    @Override
    public Map<String, String> getMapProperty(String prefix) {
        Map<String, String> map = new LinkedHashMap<>();
        for (Map.Entry<Object, Object> entry : this.properties.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            int index = key.indexOf(prefix);
            if (index == 0) {
                map.put(key.substring(index, key.length()), value);
            }
        }
        return map;
    }
}
