package com.yiwugou.homer.core.exception;

public class ExecuteException extends HomerException {
    private static final long serialVersionUID = 1L;

    public ExecuteException() {
        super();
    }

    public ExecuteException(String msg) {
        super(msg);
    }

    public ExecuteException(Throwable e) {
        super(e);
    }

    public ExecuteException(String msg, Throwable e) {
        super(msg, e);
    }
}
