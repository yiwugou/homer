package com.yiwugou.homer.core.hanlder;

import java.lang.reflect.Method;

import com.yiwugou.homer.core.util.CommonUtils;

/**
 *
 * ToStringMethodHandler
 *
 * @author zhanxiaoyong@yiwugou.com
 *
 * @since 2017年7月10日 下午3:13:47
 */
public class ToStringMethodHandler extends AbstractMethodHandler {
    private Class<?> clazz;

    private Method[] methods;

    public ToStringMethodHandler(Class<?> clazz) {
        this.clazz = clazz;
        this.methods = this.clazz.getMethods();
    }

    @Override
    public Object apply(Object[] args) throws Throwable {
        return "class is " + this.clazz.getName() + "; methods is " + CommonUtils.joinToString(",", this.methods);
    }

}
