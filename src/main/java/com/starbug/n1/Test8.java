package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 16:23
 */
@Slf4j(topic = "c.Test8")
public class Test8 {

    public static void main(String[] args) throws InterruptedException {
        log.debug("start...");

        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
            log.debug("t1-end....");
        }, "t1");

        //将t1线程设置为守护线程
        //如果所有非守护线程结束后,程序将会结束
        //如: 垃圾回收线程,Tomcat中的Acceptor和Poller线程都是手环线程,所以在tomcat收到shutdown命令后,不会等待他们完成请求
//        t1.setDaemon(true);

        t1.start();

        TimeUnit.SECONDS.sleep(1);

        log.debug("结束");
    }

}
