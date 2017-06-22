package com.yiwugou.homer.core.codec;

import java.lang.reflect.Type;

import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.exception.DecoderException;

public abstract class AbstractDecoder implements Decoder {

    public abstract Object objectDecode(final Response response, final Type returnType);

    @Override
    public Object decode(Response response, Type returnType) {
        if (returnType.equals(void.class)) {
            return null;
        }
        byte[] body = response.getBody();
        if (body == null || body.length == 0) {
            if (returnType.equals(int.class)) {
                return Integer.valueOf(0);
            } else if (returnType.equals(long.class)) {
                return Long.valueOf(0L);
            } else if (returnType.equals(short.class)) {
                return Short.valueOf((short) 0);
            } else if (returnType.equals(float.class)) {
                return Float.valueOf(0);
            } else if (returnType.equals(double.class)) {
                return Double.valueOf(0);
            } else if (returnType.equals(boolean.class)) {
                return false;
            } else {
                return null;
            }
        }
        try {
            return this.objectDecode(response, returnType);
        } catch (Exception e) {
            throw new DecoderException("body is " + new String(body, Constants.UTF_8), e);
        }
    }

}
