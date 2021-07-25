package com.starbug.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author Starbug
 * @Date 2021/7/25 17:47
 * 原子计数器
 */
public class CounterTest1 {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            demo(() -> new AtomicLong(),
                    adder -> adder.incrementAndGet());
        }

        System.out.println("===============================");

        for (int i = 0; i < 5; i++) {
            demo(() -> new LongAdder(),
                    adder -> adder.increment());
        }
    }

    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList();

        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int k = 0; k < 500000; k++) {
                    action.accept(adder);
                }
            }));
        }

        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end - start) / 1000_000);

    }

}
