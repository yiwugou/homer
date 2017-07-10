package com.yiwugou.homer.core.config;

import java.io.InputStream;
import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * PropertiesFileConfigLoader
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:10:23
 */
public class PropertiesFileConfigLoader implements ConfigLoader {
    private ConfigLoader configLoader;

    public PropertiesFileConfigLoader(String propertiesFile) {
        try {
            if (!propertiesFile.endsWith(".properties")) {
                propertiesFile += ".properties";
            }
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream(propertiesFile);
            Properties properties = new Properties();
            properties.load(input);
            this.configLoader = new PropertiesConfigLoader(properties);
            CommonUtils.close(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T loader(String key, T defaultValue) {
        return this.configLoader.loader(key, defaultValue);
    }

}
