package com.starbug.string;

import java.util.Arrays;

/**
 *  @Author Starbug
 *  @Date 2021/5/27 19:25
 */
public class Test1 {

    public static void main(String[] args) {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str1.intern() == str1);
        System.out.println(str2.intern() == str2);
        long k = 342342343234234L;

//        String str3 = "java";
//        String str4 = new String("java");

        //因为之前没有所以创建的引用和intern()返回的引用相同

        //"java在StringBuilder()之前已经出现过",所以intern()返回的引用与新创建的引用不是同一个
        int[] arr1 = new int[]{134, 432, 5, 234, 564, 324};
        System.out.println(Arrays.toString(arr1));

        int[] arr2 = new int[10];
        System.out.println(Arrays.toString(arr2));

//        System.out.println(str3 == str4);
    }

}
