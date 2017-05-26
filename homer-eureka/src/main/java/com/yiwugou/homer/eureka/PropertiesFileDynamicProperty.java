package com.yiwugou.homer.eureka;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

public class PropertiesFileDynamicProperty implements DynamicProperty {
    private DynamicProperty dynamicProperty;

    public PropertiesFileDynamicProperty(String propertiesFile) {
        try {
            if (!propertiesFile.endsWith(".properties")) {
                propertiesFile = propertiesFile + ".properties";
            }
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream(propertiesFile);
            Properties properties = new Properties();
            properties.load(input);
            this.dynamicProperty = new PropertiesDynamicProperty(properties);
            CommonUtils.close(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getIntProperty(String key, int def) {
        return this.dynamicProperty.getIntProperty(key, def);
    }

    @Override
    public String getStringProperty(String key, String def) {
        return this.dynamicProperty.getStringProperty(key, def);
    }

    @Override
    public boolean getBooleanProperty(String key, boolean def) {
        return this.dynamicProperty.getBooleanProperty(key, def);
    }

    @Override
    public double getDoubleProperty(String key, double def) {
        return this.dynamicProperty.getDoubleProperty(key, def);
    }

    @Override
    public Map<String, String> getMapProperty(String prefix) {
        return this.dynamicProperty.getMapProperty(prefix);
    }
}
