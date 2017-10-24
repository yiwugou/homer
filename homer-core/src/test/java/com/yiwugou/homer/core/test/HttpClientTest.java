package com.yiwugou.homer.core.test;

import java.io.IOException;
import java.util.HashMap;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.client.Client;
import com.yiwugou.homer.core.client.HttpClient;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.enums.MethodEnum;

public class HttpClientTest {
    public static void main(String[] args) throws IOException {
        Request request = Request.builder().method(MethodEnum.GET).url("isearch.main:2266/code").connectTimeout(3000)
                .readTimeout(3000).headers(new HashMap<String, String>()).build();
        Client client = new HttpClient();
        Response response = client.execute(request);
        String str = new String(response.getBody(), Constants.UTF_8);
        System.out.println(str);
    }
}
