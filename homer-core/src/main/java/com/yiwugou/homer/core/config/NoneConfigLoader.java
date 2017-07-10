package com.yiwugou.homer.core.config;

/**
 *
 * NoneConfigLoader
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:10:14
 */
public class NoneConfigLoader implements ConfigLoader {
    @Override
    public <T> T loader(String key, T defaultValue) {
        return defaultValue;
    }

}
