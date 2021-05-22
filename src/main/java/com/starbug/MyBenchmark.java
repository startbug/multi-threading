package com.starbug;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *  @Author Starbug
 *  @Date 2021/5/20 13:42
 *
 * @Warmup: 热身, 执行3次
 * @Measurement: 正式压测, 执行5次
 * @BenchmarkMode: 测试模式: Mode.AverageTime计算平均时间
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
public class MyBenchmark {

    static int[] ARRAY = new int[1000_000_00];

    static {
        Arrays.fill(ARRAY, 1);
    }

    @Benchmark
    public int c() throws ExecutionException, InterruptedException {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask(() -> {
            int sum = 0;
            for (int i = 0; i < 250_000_00; i++) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t2 = new FutureTask(() -> {
            int sum = 0;
            for (int i = 0; i < 250_000_00; i++) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t3 = new FutureTask(() -> {
            int sum = 0;
            for (int i = 0; i < 250_000_00; i++) {
                sum += array[i];
            }
            return sum;
        });
        FutureTask<Integer> t4 = new FutureTask(() -> {
            int sum = 0;
            for (int i = 0; i < 250_000_00; i++) {
                sum += array[i];
            }
            return sum;
        });
        new Thread(t1).start();
        new Thread(t2).start();
        new Thread(t3).start();
        new Thread(t4).start();
        return t1.get() + t2.get() + t3.get() + t4.get();
    }

    @Benchmark
    public int d() throws ExecutionException, InterruptedException {
        int[] array = ARRAY;
        FutureTask<Integer> t1 = new FutureTask(() -> {
            int sum = 0;
            for (int i = 0; i < array.length; i++) {
                sum += array[i];
            }
            return sum;
        });

        new Thread(t1).start();

        return t1.get();
    }

}
