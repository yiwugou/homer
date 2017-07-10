package com.yiwugou.homer.core.exception;

/**
 *
 * ActiveException
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:11:53
 */
public class ActiveException extends HomerException {
    private static final long serialVersionUID = 1L;

    public ActiveException() {
        super();
    }

    public ActiveException(String msg) {
        super(msg);
    }

    public ActiveException(Throwable e) {
        super(e);
    }

    public ActiveException(String msg, Throwable e) {
        super(msg, e);
    }
}
