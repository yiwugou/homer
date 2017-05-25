package com.yiwugou.homer.core.codec;

import java.lang.reflect.Type;

import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.exception.DecoderException;
import com.yiwugou.homer.core.util.CommonUtils;

public class DefaultDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type returnType) {
        if (returnType.equals(void.class)) {
            return null;
        }
        byte[] body = response.getBody();
        if (body == null || body.length == 0) {
            return null;
        }
        String str = new String(body, Constants.UTF_8);
        try {
            return CommonUtils.stringToBasic(str, returnType);
        } catch (Exception e) {
            throw new DecoderException(e);
        }

    }
}
