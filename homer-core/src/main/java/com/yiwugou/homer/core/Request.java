package com.yiwugou.homer.core;

import java.util.Map;

import com.yiwugou.homer.core.enums.MethodEnum;
import com.yiwugou.homer.core.util.AssertUtils;
import com.yiwugou.homer.core.util.CommonUtils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * Request
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:31:44
 */
@EqualsAndHashCode
@Builder
public class Request {
    @Getter
    private final MethodEnum method;
    @Getter
    private final String url;
    @Getter
    private final byte[] body;
    @Getter
    private final Map<String, String> headers;
    @Getter
    private final int connectTimeout;
    @Getter
    private final int readTimeout;

    public Request addHeaders(String name, String value) {
        AssertUtils.hasTest(name, "header name");
        AssertUtils.hasTest(value, "header value");
        this.headers.put(name, value);
        return this;
    }

    @Override
    public String toString() {
        return "Request [method=" + this.method + ", url=" + this.url + ", body="
                + CommonUtils.bytesToString(this.body, null) + ", headers=" + this.headers + ", connectTimeout="
                + this.connectTimeout + ", readTimeout=" + this.readTimeout + "]";
    }

}
