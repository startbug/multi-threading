package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 0:04
 *  唤醒睡眠中的线程,睡眠中的线程会抛出InterruptedException异常,被唤醒后进入Runnable状态
 */
@Slf4j(topic = "c.Test2")
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("to sleep.......");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.debug("interrupted...唤醒啦");
                e.printStackTrace();
            }
        }, "t1");

        t1.start(); //启动线程
        TimeUnit.SECONDS.sleep(1);  //睡眠1秒,等待t1线程进入睡眠状态
        log.debug("{}", t1.getState()); //查看线程状态TIMED_WAITING

        t1.interrupt(); //唤醒t1线程
        log.debug("interrupt.....{}", t1.getState());   //查看线程状态RUNNABLE

    }

}
