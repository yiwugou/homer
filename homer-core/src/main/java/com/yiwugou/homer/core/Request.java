package com.yiwugou.homer.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.enums.MethodEnum;
import com.yiwugou.homer.core.util.AssertUtils;

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
    private final Map<String, List<String>> headers;
    @Getter
    private final int connectTimeout;
    @Getter
    private final int readTimeout;

    public Request addHeaders(String name, String... values) {
        AssertUtils.notNull(name, "header name");
        if (values == null || values.length == 0) {
            this.headers.remove(name);
        } else {
            List<String> list = new ArrayList<>(Arrays.asList(values));
            this.headers.put(name, list);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Request [method=" + this.method + ", url=" + this.url + ", body=" + this.body == null ? "''"
                : new String(this.body, Constants.UTF_8) + ", headers=" + this.headers + ", connectTimeout="
                        + this.connectTimeout + ", readTimeout=" + this.readTimeout + "]";
    }

}
