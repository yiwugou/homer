package com.yiwugou.homer.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.codec.Decoder;

public class FastjsonDecoder implements Decoder {

    @Override
    public Object decode(Response response, Class<?> returnType) {
        byte[] bs = response.getBody();
        return JSONObject.parseObject(bs, returnType);
    }

}
