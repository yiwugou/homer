package com.yiwugou.homer.core;

import java.util.List;
import java.util.Map;

import com.yiwugou.homer.core.util.CommonUtils;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 *
 * Response
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:31:49
 */
@EqualsAndHashCode
@Builder
public class Response {
    @Getter
    private final int code;
    @Getter
    private final String message;
    @Getter
    private final Map<String, List<String>> headers;
    @Getter
    private final byte[] body;
    @Getter
    private final Request request;

    @Override
    public String toString() {
        return "Response [code=" + this.code + ", message=" + this.message + ", headers=" + this.headers + ", body="
                + CommonUtils.bytesToString(this.body, null) + ", request=" + this.request + "]";
    }

}
