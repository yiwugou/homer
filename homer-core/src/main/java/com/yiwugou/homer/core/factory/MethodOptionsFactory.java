package com.yiwugou.homer.core.factory;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.annotation.RequestConfig;
import com.yiwugou.homer.core.annotation.RequestUrl;
import com.yiwugou.homer.core.config.ConfigLoader;
import com.yiwugou.homer.core.config.MethodOptions;
import com.yiwugou.homer.core.constant.RequestDefault;
import com.yiwugou.homer.core.server.ServerHandler;

public class MethodOptionsFactory {
    private Class<?> clazz;
    private Method method;
    private ConfigLoader configLoader;
    private InstanceCreater instanceCreater;

    private String className;
    private String methodName;
    private String classMethodName;

    public MethodOptionsFactory(Method method, ConfigLoader configLoader, InstanceCreater instanceCreater) {
        this.method = method;
        this.configLoader = configLoader;
        this.instanceCreater = instanceCreater;
        this.clazz = method.getDeclaringClass();
        this.className = this.clazz.getName();
        this.methodName = this.method.getName();
        this.classMethodName = this.className + "." + this.methodName;
    }

    public MethodOptions create() {
        RequestConfig classRequestConfig = this.clazz.getAnnotation(RequestConfig.class);

        MethodOptions methodOptions = new MethodOptions();

        RequestConfig methodRequestConfig = this.method.getAnnotation(RequestConfig.class);

        this.initRetry(classRequestConfig, methodOptions, methodRequestConfig);

        this.initExecute(classRequestConfig, methodOptions, methodRequestConfig);

        this.initActive(classRequestConfig, methodOptions, methodRequestConfig);

        this.initCache(classRequestConfig, methodOptions, methodRequestConfig);

        this.initMock(classRequestConfig, methodOptions, methodRequestConfig);

        this.initLoadBalance(classRequestConfig, methodOptions, methodRequestConfig);

        this.initConnectTimeout(classRequestConfig, methodOptions, methodRequestConfig);

        this.initReadTimeout(classRequestConfig, methodOptions, methodRequestConfig);

        RequestUrl requestUrl = this.clazz.getAnnotation(RequestUrl.class);
        ServerHandler serverHandler = this.instanceCreater.createServerHandler(requestUrl, this.clazz,
                this.configLoader);
        methodOptions.setServerHandler(serverHandler);
        return methodOptions;
    }

    private void initLoadBalance(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        String classLoadBalance = this.configLoader.loader(this.className + ConfigLoader.LOAD_BALANCE,
                classRequestConfig == null ? RequestDefault.LOAD_BALANCE
                        : classRequestConfig.loadBalance().toString().toLowerCase());
        String methodLoadBalance = this.configLoader.loader(this.classMethodName + ConfigLoader.LOAD_BALANCE,
                methodRequestConfig == null ? null : methodRequestConfig.loadBalance().toString().toLowerCase());
        String loadBalance = notDef(classLoadBalance, null, methodLoadBalance);
        methodOptions.setLoadBalance(LoadBalanceFactory.createInstants(loadBalance));
    }

    private void initMock(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Boolean classMock = this.configLoader.loader(this.className + ConfigLoader.MOCK,
                classRequestConfig == null ? Boolean.TRUE : classRequestConfig.mock());
        Boolean methodMock = this.configLoader.loader(this.classMethodName + ConfigLoader.MOCK,
                methodRequestConfig == null ? null : methodRequestConfig.mock());
        methodOptions.setMock(notDef(methodMock, false, classMock));
    }

    private void initCache(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Long classCache = this.configLoader.loader(this.className + ConfigLoader.CACHE,
                classRequestConfig == null ? RequestDefault.CACHE : classRequestConfig.cache());
        Long methodCache = this.configLoader.loader(this.classMethodName + ConfigLoader.CACHE,
                methodRequestConfig == null ? RequestDefault.CACHE : methodRequestConfig.cache());
        methodOptions.setCache(notDef(methodCache, -1L, classCache));
    }

    private void initActive(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classActive = this.configLoader.loader(this.className + ConfigLoader.ACTIVE,
                classRequestConfig == null ? RequestDefault.ACTIVE : classRequestConfig.active());
        Integer methodActive = this.configLoader.loader(this.classMethodName + ConfigLoader.ACTIVE,
                methodRequestConfig == null ? RequestDefault.ACTIVE : methodRequestConfig.active());
        methodOptions.setActive(notDef(methodActive, -1, classActive));
    }

    private void initExecute(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classExecute = this.configLoader.loader(this.className + ConfigLoader.EXECUTE,
                classRequestConfig == null ? RequestDefault.EXECUTE : classRequestConfig.execute());
        Integer methodExecute = this.configLoader.loader(this.classMethodName + ConfigLoader.EXECUTE,
                methodRequestConfig == null ? RequestDefault.EXECUTE : methodRequestConfig.execute());
        methodOptions.setExecute(notDef(methodExecute, -1, classExecute));
    }

    private void initRetry(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classRetry = this.configLoader.loader(this.className + ConfigLoader.RETRY,
                classRequestConfig == null ? RequestDefault.RETRY : classRequestConfig.retry());
        Integer methodRetry = this.configLoader.loader(this.classMethodName + ConfigLoader.RETRY,
                methodRequestConfig == null ? RequestDefault.RETRY : methodRequestConfig.retry());
        methodOptions.setRetry(notDef(methodRetry, -1, classRetry));
    }

    private void initConnectTimeout(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classConnectTimeout = this.configLoader.loader(this.className + ConfigLoader.CONNECT_TIMEOUT,
                classRequestConfig == null ? RequestDefault.CONNECT_TIMEOUT : classRequestConfig.connectTimeout());
        Integer methodConnectTimeout = this.configLoader.loader(this.classMethodName + ConfigLoader.CONNECT_TIMEOUT,
                methodRequestConfig == null ? RequestDefault.CONNECT_TIMEOUT : methodRequestConfig.connectTimeout());
        methodOptions.setConnectTimeout(notDef(methodConnectTimeout, -1, classConnectTimeout));
    }

    private void initReadTimeout(RequestConfig classRequestConfig, MethodOptions methodOptions,
            RequestConfig methodRequestConfig) {
        Integer classReadTimeout = this.configLoader.loader(this.className + ConfigLoader.READ_TIMEOUT,
                classRequestConfig == null ? RequestDefault.READ_TIMEOUT : classRequestConfig.readTimeout());
        Integer methodReadTimeout = this.configLoader.loader(this.classMethodName + ConfigLoader.READ_TIMEOUT,
                methodRequestConfig == null ? RequestDefault.READ_TIMEOUT : methodRequestConfig.readTimeout());
        methodOptions.setConnectTimeout(notDef(methodReadTimeout, -1, classReadTimeout));
    }

    public static <T> T notDef(T obj, T def, T desc) {
        return (obj == null || obj.equals(def)) ? desc : obj;
    }
}
