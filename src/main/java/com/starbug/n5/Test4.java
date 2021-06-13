package com.starbug.n5;

import lombok.extern.slf4j.Slf4j;

/**
 *  @Author Starbug
 *  @Date 2021/6/12 16:13
 *  顺序输出abcabcabcabcabcabc
 *  使用基础的wait和notify
 */
@Slf4j(topic = "c.Test4")
public class Test4 {


    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(0, 30);
        for (int i = 0; i < 3; i++) {
            final int num = i;
            new Thread(() -> {
                //参数1: 传入需要打印的字符 0:a 1:b 2:c
                //参数2: 当标记为num的时候,才输出
                //参数3: 输出后,将标记修改为下一个需要输出的字符的标记
                waitNotify.print(num % 3 == 0 ? "a" : num % 3 == 1 ? "b" : "c", num, (num + 1) % 3);
            }, "t" + i).start();
        }
    }

}

/**
 * 输出内容     等待标记      下一个标记
 *    a           0             1
 *    b           1             2
 *    c           2             0
 **/
class WaitNotify {

    //等待标记
    private int flag;
    //循环次数
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (waitFlag != flag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }


}
