package com.yiwugou.homer.core.exception;

/**
 *
 * DecoderException
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:11:58
 */
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
