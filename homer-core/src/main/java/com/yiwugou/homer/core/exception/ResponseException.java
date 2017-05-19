package com.yiwugou.homer.core.exception;

import com.yiwugou.homer.core.Response;

public class ResponseException extends HomerException {

    private static final long serialVersionUID = 1L;

    public ResponseException(Response response) {
        super("Response is error, errorCode is " + response.getCode() + ",response is " + response);
    }

}
