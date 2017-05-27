package com.yiwugou.homer.core.codec;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.CommonUtils;

public class DefaultDecoder extends AbstractDecoder {

    @Override
    public Object decode(final Response response, final Type returnType) {
        return super.baseDecode(response, returnType, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                String str = new String(response.getBody(), Constants.UTF_8);
                return CommonUtils.stringToBasic(str, returnType);
            }
        });

    }
}
