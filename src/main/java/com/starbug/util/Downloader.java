package com.starbug.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 *  @Author Starbug
 *  @Date 2021/6/6 21:07
 */
public class Downloader {

    public static List<String> download() throws IOException {
        URLConnection connection = new URL("https://www.baidu.com/").openConnection();
        List<String> lines = new ArrayList();
        InputStream is = connection.getInputStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}
