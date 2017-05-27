package com.yiwugou.homer.core.codec;

import java.lang.reflect.Type;

import com.yiwugou.homer.core.Response;

public interface Decoder {
    Object decode(final Response response, final Type returnType);
}
