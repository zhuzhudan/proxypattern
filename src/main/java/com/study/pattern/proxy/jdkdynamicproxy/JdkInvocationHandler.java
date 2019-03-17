package com.study.pattern.proxy.jdkdynamicproxy;

import java.lang.reflect.Method;

public interface JdkInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
