package com.yiwugou.homer.eureka;

import java.util.Map;

public interface DynamicProperty {
    int getIntProperty(String key, int def);

    String getStringProperty(String key, String def);

    boolean getBooleanProperty(String key, boolean def);

    double getDoubleProperty(String key, double def);

    Map<String, String> getMapProperty(String prefix);
}
