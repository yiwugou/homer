package com.yiwugou.homer.core.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.Response;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.CommonUtils;

public class HttpClient implements Client {

    @Override
    public Response execute(Request request) throws IOException {
        HttpURLConnection connection = this.convertAndSend(request);
        Response response = this.convertResponse(connection, request);
        return response;
    }

    private HttpURLConnection convertAndSend(Request request) throws IOException {
        String url = request.getUrl();
        if (!url.startsWith("http")) {
            url = "http://" + url;
        }
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setConnectTimeout(request.getConnectTimeout());
        connection.setReadTimeout(request.getReadTimeout());
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod(request.getMethod().toString());

        boolean hasAcceptHeader = false;
        Integer contentLength = null;

        if (request.getHeaders() != null) {
            for (String field : request.getHeaders().keySet()) {
                if (field.equalsIgnoreCase(Constants.ACCEPT)) {
                    hasAcceptHeader = true;
                }
                String value = request.getHeaders().get(field);
                if (field.equals(Constants.CONTENT_LENGTH)) {
                    contentLength = Integer.valueOf(value);
                }
                connection.addRequestProperty(field, value);
            }
        }

        if (!hasAcceptHeader) {
            connection.addRequestProperty(Constants.ACCEPT, Constants.ACCEPT_ALL);
        }

        if (request.getBody() != null) {
            if (contentLength != null) {
                connection.setFixedLengthStreamingMode(contentLength);
            } else {
                connection.setChunkedStreamingMode(8196);
            }
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
            try {
                out.write(request.getBody());
            } finally {
                CommonUtils.close(out);
            }
        }
        return connection;
    }

    private Response convertResponse(HttpURLConnection connection, Request request) throws IOException {
        int code = connection.getResponseCode();
        String message = connection.getResponseMessage();
        if (code < 0) {
            throw new IOException(String.format("Invalid status(%s) executing %s %s", code,
                    connection.getRequestMethod(), connection.getURL()));
        }

        Map<String, List<String>> headers = connection.getHeaderFields();

        InputStream input = null;
        if (code >= 400) {
            input = connection.getErrorStream();
        } else {
            input = connection.getInputStream();
        }
        byte[] body = CommonUtils.inputStreamToBytes(input);
        CommonUtils.close(input);
        Response response = Response.builder().code(code).message(message).headers(headers).body(body).request(request)
                .build();
        return response;
    }

}
