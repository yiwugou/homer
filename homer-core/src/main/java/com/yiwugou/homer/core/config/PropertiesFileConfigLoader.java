package com.yiwugou.homer.core.config;

import java.io.InputStream;
import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

public class PropertiesFileConfigLoader extends PropertiesConfigLoader {
    public PropertiesFileConfigLoader(String file) {
        try {
            InputStream input = this.getClass().getResourceAsStream(file);
            Properties properties = new Properties();
            properties.load(input);
            super.setProperties(properties);
            CommonUtils.close(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
