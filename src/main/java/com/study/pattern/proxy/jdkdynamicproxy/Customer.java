package com.study.pattern.proxy.jdkdynamicproxy;

public class Customer implements Person {

    @Override
    public void find() {
        System.out.println("客户：有眼缘");
    }
}
