package com.yiwugou.homer.core.factory;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.Server;
import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.constant.Constants;
import com.yiwugou.homer.core.util.CommonUtils;

public class MethodOptionsFactory {
    private Class<?> clazz;
    private Method method;
    private ConfigLoader configLoader;

    private String className;
    private String methodName;
    private String classMethodName;

    public MethodOptionsFactory(Method method, ConfigLoader configLoader) {
        this.method = method;
        this.configLoader = configLoader;
        this.clazz = method.getDeclaringClass();
        this.className = this.clazz.getName();
        this.methodName = this.method.getName();
        this.classMethodName = this.className + "." + this.methodName;
    }

    private String classMethodName(String className, String methodName) {
        return className + "." + methodName;
    }

    public MethodOptions create() {
        RequestUrl requestUrl = this.clazz.getAnnotation(RequestUrl.class);
        RequestConfig classRequestConfig = this.clazz.getAnnotation(RequestConfig.class);

        MethodOptions methodOptions = new MethodOptions();

        RequestConfig methodRequestConfig = this.method.getAnnotation(RequestConfig.class);

        this.initServers(requestUrl, methodOptions);

        this.initRetry(classRequestConfig, methodOptions, methodRequestConfig);

        this.initExecute(classRequestConfig, methodOptions, methodRequestConfig);

        this.initActive(classRequestConfig, methodOptions, methodRequestConfig);

        this.initCache(classRequestConfig, methodOptions, methodRequestConfig);

        this.initMock(classRequestConfig, methodOptions, methodRequestConfig);

        this.initLoadBalance(classRequestConfig, methodOptions, methodRequestConfig);

        return methodOptions;
    }

    private void initLoadBalance(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        String classLoadBalance = classRequestConfig == null ? RequestConfig.Default.LOAD_BALANCE
                : this.configLoader.loader(this.className + ConfigLoader.LOAD_BALANCE,
                        classRequestConfig.loadBalance().toString());
        String methodLoadBalance = methodRequestConfig == null ? RequestConfig.Default.LOAD_BALANCE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.LOAD_BALANCE,
                        methodRequestConfig.loadBalance().toString());
        String loadBalance = notDef(classLoadBalance, RequestConfig.Default.LOAD_BALANCE.toLowerCase(),
                methodLoadBalance);
        methodOptions.setLoadBalance(LoadBalanceFactory.createInstants(loadBalance));
    }

    private void initMock(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Boolean classMock = classRequestConfig == null ? RequestConfig.Default.MOCK
                : this.configLoader.loader(this.className + ConfigLoader.MOCK, classRequestConfig.mock());
        Boolean methodMock = methodRequestConfig == null ? RequestConfig.Default.MOCK
                : this.configLoader.loader(this.classMethodName + ConfigLoader.MOCK, methodRequestConfig.mock());
        methodOptions.setMock(notDef(methodMock, RequestConfig.Default.MOCK, classMock));
    }

    private void initCache(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Long classCache = classRequestConfig == null ? RequestConfig.Default.CACHE
                : this.configLoader.loader(this.className + ConfigLoader.CACHE, classRequestConfig.cache());
        Long methodCache = methodRequestConfig == null ? RequestConfig.Default.CACHE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.CACHE, methodRequestConfig.cache());
        methodOptions.setCache(notDef(methodCache, RequestConfig.Default.CACHE, classCache));
    }

    private void initActive(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classActive = classRequestConfig == null ? RequestConfig.Default.ACTIVE
                : this.configLoader.loader(this.className + ConfigLoader.ACTIVE, classRequestConfig.active());
        Integer methodActive = methodRequestConfig == null ? RequestConfig.Default.ACTIVE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.ACTIVE, methodRequestConfig.active());
        methodOptions.setActive(notDef(methodActive, RequestConfig.Default.ACTIVE, classActive));
    }

    private void initExecute(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classExecute = classRequestConfig == null ? RequestConfig.Default.EXECUTE
                : this.configLoader.loader(this.className + ConfigLoader.EXECUTE, classRequestConfig.execute());
        Integer methodExecute = methodRequestConfig == null ? RequestConfig.Default.EXECUTE
                : this.configLoader.loader(this.classMethodName + ConfigLoader.EXECUTE, methodRequestConfig.execute());
        methodOptions.setExecute(notDef(methodExecute, RequestConfig.Default.EXECUTE, classExecute));
    }

    private void initServers(RequestUrl requestUrl, MethodOptions methodOptions) {
        String[] requestUrls = requestUrl.value();
        String url = this.configLoader.loader(this.className + ConfigLoader.URL,
                CommonUtils.joinToString(Constants.URL_SEPARATOR, requestUrls));

        String urls[] = url.split(Constants.URL_SEPARATOR);
        Server server = null;

        for (String u : urls) {
            if (CommonUtils.hasTest(u)) {
                if (u.endsWith("/")) {
                    u = u.substring(0, u.length() - 1);
                }
                server = new Server(u);
                methodOptions.getAvailableServers().add(server);
            }
        }
    }

    private void initRetry(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classRetry = classRequestConfig == null ? RequestConfig.Default.RETRY
                : this.configLoader.loader(this.className + ConfigLoader.RETRY, classRequestConfig.retry());
        Integer methodRetry = methodRequestConfig == null ? RequestConfig.Default.RETRY
                : this.configLoader.loader(this.classMethodName + ConfigLoader.RETRY, methodRequestConfig.retry());
        methodOptions.setRetry(notDef(methodRetry, RequestConfig.Default.RETRY, classRetry));
    }

    public static <T> T notDef(T obj, T def, T desc) {
        return (obj == null || obj.equals(def)) ? desc : obj;
    }
}
