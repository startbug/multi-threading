package com.starbug.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author Starbug
 * @Date 2021/7/24 23:35
 * ABA问题
 */
@Slf4j(topic = "c.ABAQuestion")
public class ABAQuestion {

    public static final AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start ....");
        //获取值A
        String pre = ref.get();
        //不知道这个值是否被修改过?
        other();
        TimeUnit.SECONDS.sleep(1);
        //尝试修改为C
        //这里修改的时候,无法感知得到变量已经被修改过了,只是刚好又修改回来了,虽然不会产生影响,但是有这个隐患
        log.debug("change A->C {}", ref.compareAndSet(pre, "C"));
    }

    private static void other() throws InterruptedException {
        new Thread(() -> {
            log.debug("change A->B {}", ref.compareAndSet(ref.get(), "B"));
        }, "t1").start();
        TimeUnit.MILLISECONDS.sleep(500);
        new Thread(() -> {
            log.debug("change B->A {}", ref.compareAndSet(ref.get(), "A"));
        }, "t2").start();
    }

}
