package com.starbug.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Author Starbug
 * @Date 2021/7/24 23:47
 * 修复ABA问题,添加版本号(布尔类型，判断是否被修改过，不记录被修改了几次)
 */
@Slf4j(topic = "c.FixABAQuestion2")
public class FixABAQuestion2 {

    public static void main(String[] args) throws InterruptedException {
        GarbageBag garbageBag = new GarbageBag("装满垃圾");
        //mark是一个标记,用于标识是否被修改过
        AtomicMarkableReference<GarbageBag> reference = new AtomicMarkableReference<GarbageBag>(garbageBag, true);

        log.debug("start...");
        GarbageBag pre = reference.getReference();
        log.debug("{}", pre);

        new Thread(() -> {
            log.debug("保洁阿姨打算换垃圾袋");
            boolean success = reference.compareAndSet(pre, new GarbageBag("保洁阿姨换垃圾袋"), true, false);
            log.debug("是否换成功?{}", success);
        }, "保洁阿姨").start();

        TimeUnit.SECONDS.sleep(1);
        log.debug("我打算换垃圾袋");
        boolean success = reference.compareAndSet(pre, new GarbageBag("空垃圾袋"), true, false);
        log.debug("我是否换成功?{}", success);
        log.debug("{}", reference.getReference());

    }

}

class GarbageBag {

    private String desc;

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public GarbageBag(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "GarbageBag{" +
                "desc='" + desc + '\'' +
                '}';
    }
}