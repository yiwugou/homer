package com.yiwugou.homer.core.exception;

import com.yiwugou.homer.core.Response;

/**
 *
 * ResponseException
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:12:11
 */
public class ResponseException extends HomerException {

    private static final long serialVersionUID = 1L;

    public ResponseException(Response response) {
        super("Response is error, errorCode is " + response.getCode() + ",response is " + response);
    }

}
