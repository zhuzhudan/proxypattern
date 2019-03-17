package com.study.pattern.proxy.jdkdynamicproxy;

public class Test {
    public static void main(String[] args) {
        try {
            Person person = (Person) new Agent().getInstance(new Customer());
            System.out.println(person.getClass());
            person.find();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
