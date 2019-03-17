package com.study.pattern.proxy.bussinese.dynamicproxy.jdk;

import com.study.pattern.proxy.bussinese.Logic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JDKProxyTest {
    public static void main(String[] args) {
        Object object = new JDKLogger().getInstance(new Logic());
        try {
            Method method = object.getClass().getMethod("log", null);
            method.invoke(object);
            System.out.println("----------------------------------------");
            method = object.getClass().getMethod("show", boolean.class);
            Object returnObject = method.invoke(object, true);
            System.out.println(returnObject);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
