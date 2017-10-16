package com.yiwugou.homer.eureka;

import java.util.Map;

/**
 *
 * DynamicProperty
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年10月16日 下午4:19:53
 */
public interface DynamicProperty {
    int getIntProperty(String key, int def);

    String getStringProperty(String key, String def);

    boolean getBooleanProperty(String key, boolean def);

    double getDoubleProperty(String key, double def);

    Map<String, String> getMapProperty(String prefix);
}
