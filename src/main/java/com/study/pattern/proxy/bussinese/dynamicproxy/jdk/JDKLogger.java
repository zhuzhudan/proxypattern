package com.study.pattern.proxy.bussinese.dynamicproxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;

public class JDKLogger implements InvocationHandler {
    private Object target;
    public Object getInstance(Object proxyObj){
        this.target = proxyObj;
        Class<?> clazz = proxyObj.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        System.out.println("******************************");
        Object object = method.invoke(target, args);
        if(object != null){
            System.out.println(object.toString());
        }
        System.out.println("******************************");
        after();

        return object;
    }

    private void before(){
        System.out.println("获取逻辑代码所在的类："+ target.getClass().getName());
        System.out.println("记录当前时间："+new Date());
    }

    private void after(){
        System.out.println("记录完成，记录到磁盘");
    }
}
