package com.starbug.single;

/**
 * @Author Starbug
 * @Date 2021/6/14 22:43
 * 双重检测单例模式
 */
public class DoubleCheckedSingle {

    public DoubleCheckedSingle() {
    }

    private static volatile Object single = null;

    public static Object getInstance() {
        if (single == null) {
            synchronized (DoubleCheckedSingle.class) {
                //只有首次创建对象的时候才需要上锁,一旦对象创建好,就不会再进入上锁的代码,提高效率
                if (single == null) {
                    single = new Object();
                }
            }
        }
        return single;
    }

}
