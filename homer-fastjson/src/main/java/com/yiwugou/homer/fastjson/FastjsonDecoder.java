package com.yiwugou.homer.fastjson;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONObject;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.codec.AbstractDecoder;

public class FastjsonDecoder extends AbstractDecoder {

    @Override
    public Object objectDecode(final Response response, final Type returnType) {
        Object obj = JSONObject.parseObject(response.getBody(), returnType);
        return obj;
    }

}
