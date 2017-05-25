package com.yiwugou.homer.fastjson;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONObject;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.codec.Decoder;

public class FastjsonDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type returnType) {
        if (returnType.equals(void.class)) {
            return null;
        }
        byte[] bs = response.getBody();
        Object obj = JSONObject.parseObject(bs, returnType);
        return obj;
    }

}
