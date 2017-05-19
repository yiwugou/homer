package com.yiwugou.homer.core.exception;

public class ServerException extends HomerException {

    private static final long serialVersionUID = 1L;

    public ServerException() {
        super();
    }

    public ServerException(String msg) {
        super(msg);
    }

    public ServerException(Throwable e) {
        super(e);
    }

    public ServerException(String msg, Throwable e) {
        super(msg, e);
    }
}
