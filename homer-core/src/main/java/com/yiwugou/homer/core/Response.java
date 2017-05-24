package com.yiwugou.homer.core;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * Response
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年5月24日 上午10:31:49
 */
@EqualsAndHashCode
@ToString
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

}
