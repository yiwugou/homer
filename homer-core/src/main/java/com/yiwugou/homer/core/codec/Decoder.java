package com.yiwugou.homer.core.codec;

import java.lang.reflect.Type;

import com.yiwugou.homer.core.Response;

/**
 *
 * Decoder
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:09:54
 */
public interface Decoder {
    Object decode(final Response response, final Type returnType);
}
