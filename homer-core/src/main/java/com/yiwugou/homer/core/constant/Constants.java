package com.yiwugou.homer.core.constant;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

public class Constants {
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static final String ACCEPT = "Accept";

    public static final String ACCEPT_ALL = "*/*";

    public static final String CONTENT_LENGTH = "Content-Length";

    public static final String ACCEPT_CHARSET = "Accept-Charset";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String CONTENT_ENCODING = "Content-Encoding";

    public static final String RETRY_AFTER = "Retry-After";

    public static final String ENCODING_GZIP = "gzip";

    public static final String ENCODING_DEFLATE = "deflate";

    public static final String URL_SEPARATOR = ";";

    public static final Pattern COMMA_SPLIT_PATTERN = Pattern.compile("\\s*[,]+\\s*");

    public static final Pattern SEMICOLON_SPLIT_PATTERN = Pattern.compile("\\s*[;]+\\s*");
}
