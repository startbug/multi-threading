package com.starbug.n1;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/5/23 15:42
 *  两阶段终止模式
 *  isInterrupted: 查看线程是否被打断(不清除打端标记)
 *  interrupted: 查看线程是否被打断(清除打断标记)
 */
@Slf4j(topic = "c.Test6")
public class Test6 {

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        TimeUnit.SECONDS.sleep(4);

        twoPhaseTermination.stop();
    }

}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {

                Thread currentThread = Thread.currentThread();

                if (currentThread.isInterrupted()) {
                    log.debug("被打端,处理后事");
                    break;
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //因为线程在sleep、wait和join期间被打断,其打断表示会被置为false
                    // 所以在异常捕获后通过interrupt方法重新设一遍打断标识,下一次循环就可以进入处理阶段
//                    currentThread.interrupt();
//                    log.debug("打断标记是否被重置(isInterrupted):{}", currentThread.isInterrupted());
//                    log.debug("打断标记是否被重置(isInterrupted):{}", currentThread.isInterrupted());
//                    log.debug("打断标记是否被重置(isInterrupted):{}", currentThread.isInterrupted());
//                    log.debug("打断标记是否被重置(interrupted):{}", Thread.interrupted());
//                    log.debug("打断标记是否被重置(interrupted):{}", Thread.interrupted());
//                    log.debug("打断标记是否被重置(interrupted):{}", Thread.interrupted());
                }
            }
        });
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
    }

}
