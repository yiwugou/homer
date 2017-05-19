package com.yiwugou.homer.core.exception;

public class HomerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HomerException() {
        super();
    }

    public HomerException(String msg) {
        super(msg);
    }

    public HomerException(Throwable e) {
        super(e);
    }

    public HomerException(String msg, Throwable e) {
        super(msg, e);
    }

}
