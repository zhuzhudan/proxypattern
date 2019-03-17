package com.study.pattern.proxy.bussinese.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Date;

public class CGLibLogger implements MethodInterceptor {
    Class<?> target;

    public Object getInstance(Class<?> clazz) throws Exception{
        this.target = clazz;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        System.out.println("******************************");
        Object object = methodProxy.invokeSuper(o,objects);
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
