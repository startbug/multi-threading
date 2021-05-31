package com.starbug.jol;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

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
public class TestBiased2 {

    public static void main(String[] args) throws InterruptedException {

        Cat cat1 = new Cat();

        cat1.hashCode();    //会禁用掉这个对象的偏向锁,直接使用轻量级锁
        //hashcode只有在使用的时候才会生成，并且写入到对象头的MarkWord中
        //因为偏向锁需要使用MarkWord中的54位来存储线程Id
        //但是由于hashCode写入就会占用了31位
        //空间不足,对象由可偏向变成不可偏向

        log.debug(ClassLayout.parseInstance(cat1).toPrintable());

        synchronized (cat1) {
            log.debug(ClassLayout.parseInstance(cat1).toPrintable());
        }

        log.debug(ClassLayout.parseInstance(cat1).toPrintable());
    }

}

class Cat {

}
