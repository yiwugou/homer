package com.yiwugou.homer.fastjson;

import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import com.alibaba.fastjson.JSONObject;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.codec.AbstractDecoder;

public class FastjsonDecoder extends AbstractDecoder {

    @Override
    public Object decode(final Response response, final Type returnType) {
        return super.baseDecode(response, returnType, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Object obj = JSONObject.parseObject(response.getBody(), returnType);
                return obj;
            }
        });

    }

}
