package com.starbug.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/30 14:32
 *  偏向锁不延时: VMOptions: -XX:BiasedLockingStartupDelay=0
 *  正常情况下,会再启动的几秒后才会开启偏向锁可以通过sleep几秒后在打印新的对象的对象头信息
 *  对象头最后三位是101: (object header: mark)     0x0000000000000005
 *
 *  ------------------------------------------------
 *  -XX:-UseBiasedLocking
 *  -UseBiasedLocking前面的减号表示禁用,这里是禁用偏向锁
 *  在多线程竞争的场景下，偏向锁不适合
 *  代码执行后:
 *  最后两位: 01 表示无锁状态(要将数字转换成二进制)
 *  (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
 *  最后两位: 00 表示加上了轻量级锁(要将数字转换成二进制)
 *  (object header: mark)     0x0000000002f2f4e8 (thin lock: 0x0000000002f2f4e8)
 *  最后两位: 01 表示无锁状态(要将数字转换成二进制)
 *  (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
 *
 *  ----------------------------------------------------
 *  禁用了偏向锁,就会优先使用轻量级锁
 *  优先级: 偏向锁->轻量级锁->重量级锁
 */
@Slf4j(topic = "c.TestBiased")
public class TestBiased {

    public static void main(String[] args) throws InterruptedException {

        Dog dog1 = new Dog();
        log.debug(ClassLayout.parseInstance(dog1).toPrintable());

        synchronized (dog1) {
            // 偏向锁的情况下:
            // (object header: mark)     0x0000000003122805 (biased: 0x000000000000c48a; epoch: 0; age: 0)
            // 加锁后，对象头的MarkWord会加上线程ID，这个Id是操作系统赋予的,和java中的ThreadId无关
            log.debug(ClassLayout.parseInstance(dog1).toPrintable());
        }

        // 偏向锁的情况下:
        // 解锁后,对象头仍然会有线程Id信息,偏行锁
        log.debug(ClassLayout.parseInstance(dog1).toPrintable());

    }

}

class Dog {

}
