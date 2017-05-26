package com.yiwugou.homer.core.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.annotation.RequestHeader;
import com.yiwugou.homer.core.annotation.RequestHeaders;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.server.Server;
import com.yiwugou.homer.core.util.CommonUtils;

public class RequestFactory {
    private Class<?> clazz;
    private Method method;
    private MethodOptions methodOptions;
    private Object[] args;
    private Server server;

    private Map<String, Object> formMap = new HashMap<>();

    public RequestFactory(Method method, MethodOptions methodOptions, Object[] args, Server server) {
        this.method = method;
        this.methodOptions = methodOptions;
        this.args = args;
        this.server = server;
        this.clazz = method.getDeclaringClass();
    }

    public Request create() {
        RequestMapping requestMapping = this.method.getAnnotation(RequestMapping.class);
        String path = this.processParameterAnnotations();

        byte[] body = this.formMapToBytes();
        Map<String, String> headers = this.processHeaders();

        if (!this.server.isAlive()) {
            throw new ServerException(this.server + " is not available ");
        }

        Request request = Request.builder().method(requestMapping.method()).url(this.server.getHostPort() + "/" + path)
                .body(body).headers(headers).connectTimeout(this.methodOptions.getConnectTimeout())
                .readTimeout(this.methodOptions.getReadTimeout()).build();
        return request;

    }

    private Map<String, String> processHeaders() {
        RequestHeaders clazzHeader = this.clazz.getAnnotation(RequestHeaders.class);
        RequestHeaders methodHeader = this.method.getAnnotation(RequestHeaders.class);
        Map<String, String> headerMap = new HashMap<>();
        this.processHeaderMap(clazzHeader, headerMap);
        this.processHeaderMap(methodHeader, headerMap);
        return headerMap;
    }

    private void processHeaderMap(RequestHeaders requestHeader, Map<String, String> headerMap) {
        if (requestHeader != null && !CommonUtils.isEmpty(requestHeader.value())) {
            RequestHeader[] headers = requestHeader.value();
            for (RequestHeader header : headers) {
                if (CommonUtils.hasTest(header.name()) && CommonUtils.hasTest(header.value())) {
                    headerMap.put(header.name(), header.value());
                }
            }
        }
    }

    private byte[] formMapToBytes() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : this.formMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(key + "=" + value);
            }
        }
        return sb.toString().getBytes(Constants.UTF_8);
    }

    private String processParameterAnnotations() {
        Class<?> clazz = this.method.getDeclaringClass();
        RequestUrl requestUrl = clazz.getAnnotation(RequestUrl.class);
        RequestMapping requestMapping = this.method.getAnnotation(RequestMapping.class);

        String value = requestMapping.value();

        Class<?>[] paramTypes = this.method.getParameterTypes();
        Annotation[][] paramAnnotations = this.method.getParameterAnnotations();
        for (int i = 0, len = paramAnnotations.length; i < len; ++i) {
            Class<?> paramType = paramTypes[i];
            Annotation[] annotations = paramAnnotations[i];
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                Object arg = this.args[i];
                if (annotationType.equals(RequestParam.class)) {
                    if (Map.class.isAssignableFrom(paramType)) {
                        Map<?, ?> argMap = (Map<?, ?>) arg;
                        for (Map.Entry<?, ?> entry : argMap.entrySet()) {
                            this.formMap.put(entry.getKey().toString(), entry.getValue());
                        }
                    } else {
                        String name = RequestParam.class.cast(annotation).value();
                        if (CommonUtils.hasTest(name)) {
                            if (value.contains("{" + name + "}")) {
                                value = value.replaceAll("\\{" + name + "\\}", CommonUtils.nullToEmptyString(arg));
                            } else {
                                this.formMap.put(name, arg);
                            }
                        }
                    }
                }
            }
        }
        if (value.startsWith("/")) {
            value = value.substring(1);
        }
        return value;
    }

    private static String expand(String str, Map<String, Object> param) {
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (str.contains("{" + name + "}")) {
                str = str.replaceAll("\\{" + name + "\\}", CommonUtils.nullToEmptyString(value));
            }
        }
        return str;
    }
}
