package com.yiwugou.homer.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.yiwugou.homer.core.util.CommonUtils;

public class FileConfigLoader extends PropertiesConfigLoader {
    public FileConfigLoader(String file) throws IOException {
        InputStream input = this.getClass().getResourceAsStream(file);
        Properties properties = new Properties();
        properties.load(input);
        super.setProperties(properties);
        CommonUtils.close(input);
    }

}
