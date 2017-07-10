package com.yiwugou.homer.core.exception;

/**
 *
 * HomerException
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:12:07
 */
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
