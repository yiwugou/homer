package com.yiwugou.homer.core.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

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
        final HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();

        connection.setConnectTimeout(request.getConnectTimeout());
        connection.setReadTimeout(request.getReadTimeout());
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod(request.getMethod().toString());

        Collection<String> contentEncodingValues = request.getHeaders().get(Constants.CONTENT_ENCODING);
        boolean gzipEncodedRequest = contentEncodingValues != null
                && contentEncodingValues.contains(Constants.ENCODING_GZIP);
        boolean deflateEncodedRequest = contentEncodingValues != null
                && contentEncodingValues.contains(Constants.ENCODING_DEFLATE);

        boolean hasAcceptHeader = false;
        Integer contentLength = null;
        for (String field : request.getHeaders().keySet()) {
            if (field.equalsIgnoreCase(Constants.ACCEPT)) {
                hasAcceptHeader = true;
            }
            for (String value : request.getHeaders().get(field)) {
                if (field.equals(Constants.CONTENT_LENGTH)) {
                    if (!gzipEncodedRequest && !deflateEncodedRequest) {
                        contentLength = Integer.valueOf(value);
                        connection.addRequestProperty(field, value);
                    }
                } else {
                    connection.addRequestProperty(field, value);
                }
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
            if (gzipEncodedRequest) {
                out = new GZIPOutputStream(out);
            } else if (deflateEncodedRequest) {
                out = new DeflaterOutputStream(out);
            }
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

        byte[] body = new byte[0];

        InputStream input = null;
        if (code >= 400) {
            input = connection.getErrorStream();
        } else {
            input = connection.getInputStream();
        }
        body = CommonUtils.inputStreamToBytes(input);
        CommonUtils.close(input);
        return new Response(code, message, headers, body, request);
    }

}
