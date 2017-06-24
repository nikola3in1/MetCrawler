package com.nikola.WebCrawlerApp;

import org.jsoup.Jsoup;

import java.io.*;
import java.util.ArrayList;

public class Generate {

    public Generate() {
    }

    ///Generisanje
    public static void createTxt(String url, String name, int siteId) throws IOException {
        if (siteId == 0) {
            String[] text = printOutLyrics(url);
            String rawLanguage = Language.getLanguage(text[0]+text[1]+text[2]+text[3]+text[4]+text[5]);
            int index = rawLanguage.indexOf("(");
            String language = rawLanguage.substring(index);
            if(language.contains("abs") || language.equals(null) || language.equals("()")){
                language = "";
            }

            PrintStream out = new PrintStream(new FileOutputStream(name + language +".txt"));
            for (String s : text) {
                out.print(s + "\n");
            }
            out.close();
        } else if (siteId ==1) {

            String[] text1 = printOutLyrics1(url);
            if(text1.length>0){
            String rawLanguage1 = Language.getLanguage(text1[0]);
            int index = rawLanguage1.indexOf("(");
            String language1 = rawLanguage1.substring(index);
            if(language1.contains("abs") || language1.equals(null) || language1.equals("()")){
                language1 = "";
            }
            PrintStream out = new PrintStream(new FileOutputStream(name +language1+ ".txt"));
            for (String s : text1) {
                out.print(s + "\n");
                }
            out.close();
            }
        }

    }

    public static void createDirectory(String nameRaw) throws FileNotFoundException {


        File theDir = new File(nameRaw);
        if (!theDir.exists()) {
            boolean result = false;

            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                //handle it
            }
            if (result) {
                System.out.println("DIR created");
            }
        }

    }

    public static String[] printOutLyrics(String url) throws IOException {
        String h;
        String q;
        String[] c = new String[100000];
        String temp = "";
        int u = 0;
        WebCrawler w = new WebCrawler();
        try {
            h = w.getUrlSource(url);
            String[] v = h.split("<center><p id=\"lyric\">");
            String[] r = v[1].split("</p></center>");
            q = r[0];
            for (int i = 0; i < q.length(); i++) {
                if (q.charAt(i) == '<') {
                    u++;
                }
            }
            System.out.println(html2title(h));
            c = q.split("<br />");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static String[] printOutLyrics1(String url) throws IOException {
        ArrayList AR = new ArrayList(200);
        WebCrawler w = new WebCrawler();
        String h;
        h = w.getUrlSource(url);
        String[] h1 = h.split("<br><br><br><br>");
        String[] h2 = h1[0].split("<div class=\"texts\">");
        String[] h3 = h2[1].split("<br/>");
        AR = new ArrayList(h3.length);
        for (int i =0; i < h3.length;i++) {
            if(!h3[i].contains("div")){
                AR.add(html2text(h3[i]));
            }
        }

        String[] text = new String[AR.size()];
        AR.toArray(text);
        return text;
    }

    public static String html2title(String html) {
        return Jsoup.parse(html).title();
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();

    }

}
