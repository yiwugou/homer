package com.yiwugou.homer.core.codec;

import com.yiwugou.homer.core.Response;

public interface Decoder {
    Object decode(Response response, Class<?> returnType);
}
