package com.study.pattern.proxy.jdkdynamicproxy;

import java.lang.reflect.Method;

public class Agent implements JdkInvocationHandler {

    private Customer target;

    public Object getInstance(Customer target) throws Exception{
        this.target = target;
        Class<?> clazz = target.getClass();
        return JdkProxy.newProxyInstance(new JdkClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        method.invoke(this.target, args);
        after();
        return null;
    }

    private void before(){
        System.out.println("中介：提出你的需求，立即帮你搜索");
    }

    private void after(){
        System.out.println("中介：已经搜索完毕");
    }
}
