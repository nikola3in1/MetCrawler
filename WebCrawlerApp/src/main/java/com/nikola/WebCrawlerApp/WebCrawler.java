package com.nikola.WebCrawlerApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebCrawler {
    public static String getUrlSource(String url) throws IOException {
        URL sup = new URL(url);
        URLConnection yc = sup.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine+"\n");
        in.close();

        return a.toString();
    }





    }
