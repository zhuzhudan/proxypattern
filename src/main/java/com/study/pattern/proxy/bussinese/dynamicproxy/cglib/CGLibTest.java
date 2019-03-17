package com.study.pattern.proxy.bussinese.dynamicproxy.cglib;

public class CGLibTest {
    public static void main(String[] args) {
        try {
            Busniese busniese = (Busniese)new CGLibLogger().getInstance(Busniese.class);

            busniese.log();
            System.out.println("----------------------------------------");
            String retunString = busniese.show(true);
            System.out.println(retunString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
