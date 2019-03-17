package com.study.pattern.proxy.bussinese.staticproxy;

import com.study.pattern.proxy.bussinese.ILogger;

import java.util.Date;

public class StaticLogger implements ILogger {
    private ILogger logger;

    public StaticLogger(ILogger logger){
        this.logger =logger;
    }

    @Override
    public void log() {
        System.out.println("获取逻辑代码所在的类："+ logger.getClass().getName());
        System.out.println("记录当前时间："+new Date());
        System.out.println("******************************");
        this.logger.log();
        System.out.println("******************************");
        System.out.println("记录完成，记录到磁盘");
    }

    @Override
    public String show(boolean isShow) {
        if(isShow) {
            String string = "所在类" + logger.getClass().getName() + "\r\n";
            string += logger.show(isShow);
            return string;
        }
        return "";
    }
}
