package com.starbug.io;

import java.io.*;

/**
 *  @Author Starbug
 *  @Date 2021/6/10 12:57
 */
public class Test1 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(Test1.class.getClassLoader().getResource("logback.xml").getFile().replaceAll("%20", " "));
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader fr = new BufferedReader(isr);

        String text = fr.readLine();
        while (text != null) {
            text = fr.readLine();
            System.out.println(text);
        }

    }
}
