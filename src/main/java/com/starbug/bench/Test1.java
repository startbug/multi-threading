package com.starbug.bench;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/31 13:54
 *  JIT 即时编译器会对b方法进行优化,因为o对象没有逃出b方法,即这个锁并不会有其他人使用
 *  所以锁加不加都是一样的结果,就会将加锁的操作优化掉
 *
 *  通过JVM参数进行关闭优化功能
 *  java -XX:EliminateLocks -jar xxx.jar
 */
@Fork(1)
@BenchmarkMode(Mode.AverageTime)        //测试模式,计算平均时间
@Warmup(iterations = 3)                 //预热次数
@Measurement(iterations = 5)            //正式测试次数
@OutputTimeUnit(TimeUnit.NANOSECONDS)   //统计时间单位:纳秒值
public class Test1 {

    static int x = 0;

    @Benchmark
    public void a() {
        x++;
    }

    @Benchmark
    // JIT 即时编译器
    public void b() {
        Object o = new Object();
        synchronized (o) {
            x++;
        }
    }

}
