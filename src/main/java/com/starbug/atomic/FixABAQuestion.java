package com.starbug.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author Starbug
 * @Date 2021/7/24 23:47
 * 修复ABA问题,添加版本号(记录一个int值，记录被修改的次数)
 */
@Slf4j(topic = "c.FixABAQuestion")
public class FixABAQuestion {

    public static final AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start ....");
        //获取值A
        String pre = ref.getReference();
        //获取版本号
        int version = ref.getStamp();
        log.debug("最初版本号：{}", version);
        //不知道这个值是否被修改过?
        other();
        TimeUnit.SECONDS.sleep(1);
        //尝试修改为C
        //这里修改的时候,无法感知得到变量已经被修改过了,只是刚好又修改回来了,虽然不会产生影响,但是有这个隐患
        log.debug("执行其他方法后的版本号：{}", ref.getStamp());
        log.debug("change A->C {}", ref.compareAndSet(pre, "C", version, version + 1));
        log.debug("最终版本号：{}", ref.getStamp());
    }

    private static void other() throws InterruptedException {
        new Thread(() -> {
            int version = ref.getStamp();
            log.debug("change A->B {}，版本号：{}", ref.compareAndSet(ref.getReference(), "B", version, version + 1), ref.getStamp());
        }, "t1").start();
        TimeUnit.MILLISECONDS.sleep(500);
        new Thread(() -> {
            int version = ref.getStamp();
            log.debug("change B->A {}，版本号：{}", ref.compareAndSet(ref.getReference(), "A", version, version + 1), ref.getStamp());
        }, "t2").start();
    }


}
