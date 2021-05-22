package com.starbug.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/21 13:10
 */
@Slf4j(topic = "c.Test2")
public class Test2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask(() -> {
            log.debug("running");
            TimeUnit.SECONDS.sleep(1);
            return 100;
        });

        Thread t1 = new Thread(task);
        t1.start();

        log.debug("{}", task.get());

    }

}
