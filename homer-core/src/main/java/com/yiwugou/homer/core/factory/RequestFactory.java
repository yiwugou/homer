package com.yiwugou.homer.core.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.yiwugou.homer.core.Request;
import com.yiwugou.homer.core.annotation.RequestBody;
import com.yiwugou.homer.core.annotation.RequestHeader;
import com.yiwugou.homer.core.annotation.RequestHeaders;
import com.yiwugou.homer.core.annotation.RequestMapping;
import com.yiwugou.homer.core.annotation.RequestParam;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.exception.ServerException;
import com.yiwugou.homer.core.server.Server;
import com.yiwugou.homer.core.util.AssertUtils;
import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * RequestFactory
 * 
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:12:48
 */
public class RequestFactory {
    private Class<?> clazz;
    private Method method;
    private MethodOptions methodOptions;
    private Object[] args;
    private Server server;

    private String bodyString;
    private Map<String, Object> formMap = new HashMap<>();

    public RequestFactory(Method method, MethodOptions methodOptions, Object[] args, Server server) {
        this.method = method;
        this.methodOptions = methodOptions;
        this.args = args;
        this.server = server;
        this.clazz = method.getDeclaringClass();
        this.initBodyString();
    }

    public Request create() {
        RequestMapping requestMapping = this.method.getAnnotation(RequestMapping.class);
        String path = this.processParamAnnotationsReturnPath();

        if (!this.server.isAlive()) {
            throw new ServerException(this.server + " is not available ");
        }

        Request request = Request.builder().method(requestMapping.method()).url(this.server.getHostPort() + "/" + path)
                .body(this.body()).headers(this.processHeaders()).connectTimeout(this.methodOptions.getConnectTimeout())
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

    private byte[] body() {
        if (CommonUtils.hasTest(this.bodyString)) {
            return this.bodyString.getBytes(Constants.UTF_8);
        }
        return this.formMapToBytes();
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

    private void initBodyString() {
        RequestBody requestBody = this.method.getAnnotation(RequestBody.class);
        if (requestBody != null) {
            this.bodyString = requestBody.value();
        }
    }

    private String processParamAnnotationsReturnPath() {
        RequestMapping requestMapping = this.method.getAnnotation(RequestMapping.class);
        AssertUtils.notNull(requestMapping, this.method + " requestMapping ");
        String path = requestMapping.value();

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
                            String name = entry.getKey().toString();
                            String value = CommonUtils.nullToEmptyString(entry.getValue());
                            this.formMap.put(name, entry.getValue());
                            if (CommonUtils.hasTest(this.bodyString) && this.bodyString.contains("{" + name + "}")) {
                                this.bodyString = this.bodyString.replaceAll("\\{" + name + "\\}", value);
                            }
                        }
                    } else {
                        String name = RequestParam.class.cast(annotation).value();
                        if (CommonUtils.hasTest(name)) {
                            String argValue = CommonUtils.nullToEmptyString(arg);
                            if (CommonUtils.hasTest(this.bodyString) && this.bodyString.contains("{" + name + "}")) {
                                this.bodyString = this.bodyString.replaceAll("\\{" + name + "\\}", argValue);
                            }

                            if (path.contains("{" + name + "}")) {
                                path = path.replaceAll("\\{" + name + "\\}", CommonUtils.urlEncode(argValue));
                            } else {
                                this.formMap.put(name, argValue);
                            }
                        }
                    }
                }
            }
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

}
