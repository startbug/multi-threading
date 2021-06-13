package com.starbug.n6;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 20:10
 *  两阶段终止模式--改进版
 */
@Slf4j(topic = "c.Test2")
public class Test2 {

    public static void main(String[] args) {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoPhaseTermination.stop();
    }

}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;
    //必须加上volatile,防止stop被其他线程修改后,其他线程不能及时看到值,无法结束程序
    private volatile boolean stop = false;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                //是否被打断
                if (stop) {
                    log.info("处理后事...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("执行监控记录....");
                } catch (InterruptedException e) {
                }
            }
        }, "monitor");
        monitor.start();
    }

    public void stop() {
        monitor.interrupt();
        this.stop = true;
    }
}
