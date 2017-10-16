package com.yiwugou.homer.eureka;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * PropertiesFileDynamicProperty
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:21:01
 */
public class PropertiesFileDynamicProperty implements DynamicProperty {
    private DynamicProperty dynamicProperty;

    private static final String PROPERTIES_FILE_SUFFIX = ".properties";

    public PropertiesFileDynamicProperty(String propertiesFile) {
        try {
            if (!propertiesFile.endsWith(PROPERTIES_FILE_SUFFIX)) {
                propertiesFile = propertiesFile + PROPERTIES_FILE_SUFFIX;
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
