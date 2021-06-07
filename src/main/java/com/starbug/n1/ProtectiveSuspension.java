package com.starbug.n1;

import com.starbug.util.Downloader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  @Author Starbug
 *  @Date 2021/6/6 21:01
 *  保护性暂停
 *  一个线程需要使用另一个线程的结果
 *  首先需要有一个保护对象,对象有一个成员变量装载数据
 *  提供get和set方法
 *  set方法,锁住当前对象,给成员变量赋值,并且唤醒其他等待的线程
 *  get方法,判断需要的结果是否为空,空则进入wait,当被唤醒,在进行判断结果是否为空,不为空则返回数据
 */
@Slf4j(topic = "c.ProtectiveSuspension")
public class ProtectiveSuspension {
    //线程1等待 线程2下载
    public static void main(String[] args) throws InterruptedException {
        GuardedObject guardedObject = new GuardedObject();

        new Thread(() -> {
            synchronized (guardedObject) {
                log.debug("等待数据....");
                List<String> list = (List<String>) guardedObject.get();
                log.debug("得到数据....");
                log.debug("{}", list.size());
            }
        }, "等待线程").start();

        TimeUnit.SECONDS.sleep(3);

        new Thread(() -> {
            synchronized (guardedObject) {
                List<String> list = null;
                try {
                    log.debug("下载数据中....");
                    list = Downloader.download();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.debug("下载完成...");
                guardedObject.complete(list);
            }
        }, "下载线程").start();
    }
}

//增加超时效果
class GuardedObject {
    //结果
    private Object response;

    //获取结果
    public Object get() {
        synchronized (this) {
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response) {
        synchronized (this) {
            //产生结果给成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }
}
