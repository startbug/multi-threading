package com.starbug.atomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Author Starbug
 * @Date 2021/7/25 16:23
 * 原子数组
 */
public class AtomicArrayTest1 {

    public static void main(String[] args) {
        //自增后,数据有误
        demo(() -> new int[10],
                array -> array.length,
                (array, index) -> array[index]++,
                (array) -> System.out.println(Arrays.toString(array)));

        //使用原子数组,计算正确
        demo(() -> new AtomicIntegerArray(10),
                array -> array.length(),
                (array, index) -> array.getAndIncrement(index), //相当于i++操作,方法形参为操作的元素下标
                System.out::println);
    }

    public static <T> void demo(Supplier<T> arraySupplier,
                                Function<T, Integer> lengthFun,
                                BiConsumer<T, Integer> putConsumer,
                                Consumer<T> printConsumer) {

        List<Thread> ts = new ArrayList();
        //获取数组
        T array = arraySupplier.get();
        //获取数组长度
        int length = lengthFun.apply(array);

        for (int i = 0; i < length; i++) {
            ts.add(new Thread(() -> {
                //每个线程操作10000次
                for (int k = 0; k < 10000; k++) {
                    //传入数组和下标,每个线程对下表操作1000次,10个线程,则每个下标操作1w次,操作为自增操作
                    putConsumer.accept(array, k % length);
                }
            }));
        }
        //启动所有线程
        ts.forEach(Thread::start);
        //等待所有线程结束
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        //打印数组
        printConsumer.accept(array);
    }

}
