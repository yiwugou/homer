package com.yiwugou.homer.eureka;

import java.util.regex.Pattern;

/**
 *
 * Constants
 *
 * @author zhanxiaoyong
 *
 * @since 2017年5月11日 下午4:53:21
 */
public class Constants {

    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

    public static final String DEFAULT_CONFIG_NAMESPACE = "eureka";

    public static final String DEFAULT_CONFIG_FILE = "eureka-client";

    public static final String HTTP = "http://";

    public static final String NAMESPACE_RIBBON_ID_SPLIT = ".";
}
