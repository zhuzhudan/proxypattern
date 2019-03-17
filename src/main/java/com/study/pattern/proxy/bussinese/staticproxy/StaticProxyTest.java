package com.study.pattern.proxy.bussinese.staticproxy;

import com.study.pattern.proxy.bussinese.Logic;

public class StaticProxyTest {
    public static void main(String[] args) {
        StaticLogger logger = new StaticLogger(new Logic());
        logger.log();
        String l = logger.show(true);
//        String l = logger.show(false);
        System.out.println();
        System.out.println(l);
    }
}
