package com.study.pattern.proxy.bussinese;

public class Logic implements ILogger {
    @Override
    public void log() {
        System.out.println("业务代码：记录详细的错误信息");
    }

    @Override
    public String show(boolean isShow) {
        if (isShow)
            return "业务代码：详细信息显示到控制台";
        return "";
    }
}
