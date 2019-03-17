package com.study.pattern.proxy.bussinese.dynamicproxy.cglib;

public class Busniese {
    public void log() {
        System.out.println("没有实现接口，业务代码：记录详细的错误信息");
    }

    public String show(boolean isShow) {
        if (isShow)
            return "没有实现接口，业务代码：详细信息显示到控制台";
        return "";
    }
}
