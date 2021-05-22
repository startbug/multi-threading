package com.starbug.n3;

/**
 *  @Author Starbug
 *  @Date 2021/5/22 12:44
 */
public class TestFrames {
    public static void main(String[] args) {
        method1(10);
    }

    private static void method1(int i) {
        int y = i + 1;
        Object m = method2();
        System.out.println(m);
    }

    private static Object method2() {
        Object o = new Object();
        return o;
    }
}
