package com.yiwugou.homer.core.exception;

public class DecoderException extends HomerException {
    private static final long serialVersionUID = 1L;

    public DecoderException() {
        super();
    }

    public DecoderException(String msg) {
        super(msg);
    }

    public DecoderException(Throwable e) {
        super(e);
    }

    public DecoderException(String msg, Throwable e) {
        super(msg, e);
    }
}
