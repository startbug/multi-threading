package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/6 21:54
 */
@Slf4j(topic = "c.ProtectiveSuspension2")
public class ProtectiveSuspension2 {
    //线程1等待 线程2下载
    public static void main(String[] args) throws InterruptedException {
        GuardedObject2 guardedObject = new GuardedObject2();

        new Thread(() -> {
            log.debug("begin");
            Object o = guardedObject.get(1000);
            log.debug("{}", o);
        }, "等待线程").start();

        new Thread(() -> {
            log.debug("begin");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            guardedObject.complete(new Object());
            log.debug("下载完成...");
        }, "下载线程").start();
    }
}

//增加超时效果
class GuardedObject2 {
    //结果
    private Object response;

    //获取结果
    public Object get(long timeout) {
        synchronized (this) {
            long base = System.currentTimeMillis();    //记录最开始的时间戳
            long delay = 0;  //记录已经用了多少时间

            while (response == null) {
                //已花费时间>=用户设置的超时时间,则结束循环
                if (delay >= timeout) {
                    break;
                }
                try {
                    //如果线程在等待的时候被错误唤醒(没到超时时间就被唤醒)
                    //判断如果还是没有数据,则继续等待,但是等待的时间需要减去之前已经花费的时间
                    this.wait(timeout - delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                delay = System.currentTimeMillis() - base;  //记录已花费的时间
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response) {
        synchronized (this) {
            //产生结果给成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}
