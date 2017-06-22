package com.yiwugou.homer.core.codec;

import java.lang.reflect.Type;

import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.CommonUtils;

public class DefaultDecoder extends AbstractDecoder {
    @Override
    public Object objectDecode(final Response response, final Type returnType) {
        String str = new String(response.getBody(), Constants.UTF_8);
        return CommonUtils.stringToBasic(str, returnType);
    }
}
