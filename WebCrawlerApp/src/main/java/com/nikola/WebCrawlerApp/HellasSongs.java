package com.nikola.WebCrawlerApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;


public class HellasSongs extends Generate {


    public static void crawl() throws IOException {
        long startTime = System.currentTimeMillis();
        int brojacP = 0;
        int brojacIz = 0;

        Author temp;
        createDirectory("HellasSongs/");
        for (int i = 1; i <= 7; i++) { //crawlovanje kroz stranice 1-7 i prikupljanje autora
            temp = getAuthors(i);
            Integer[] authors = temp.getSongList();
            for (int j = 0; j < authors.length; j++) { //prikupljanje pesama
                brojacIz++;
                char letter = temp.getAuthor(j).toCharArray()[0];
                createDirectory("HellasSongs/" + letter);
                createDirectory("HellasSongs/" + letter + '/' + temp.getAuthor(j));
                Songs temp2 = getSongs(authors[j]);
                Integer[] songs = temp2.getSongIDs();
                for (int k = 0; k < songs.length; k++) { //Generisanje txt fajla
                    if (songs[k] == -1) {
                    } else {
                        createTxt("http://hellas-songs.ru/en/song/"+songs[k]+'/',"HellasSongs/" + letter + '/' + temp.getAuthor(j)+'/'+temp.getAuthor(j) + " - "+ temp2.getSongName(k),1);
                        System.out.println("author " + temp.getAuthor(j) + " song " + songs[k]);
                        System.out.print("Crawl done, number of authors : " + brojacIz);
                        System.out.println(" number of songs : " + brojacP);
                        brojacP++;

                    }

                }
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.print("Crawl done, number of authors : " + brojacIz);
        System.out.println(" number of songs : " + brojacP);
        System.out.println(totalTime + " milisecunds taken :)");
    }

    public static Author getAuthors(Integer integer) throws IOException {
        Document doc;
        String h;
        String[] h1;
        ArrayList AL = new ArrayList();
        ArrayList authorNames = new ArrayList();
        Author author = new Author();
        if (integer > 1) {
            doc = Jsoup.connect("http://hellas-songs.ru/en/singer/0/" + integer + "/").get();
        } else {
            doc = Jsoup.connect("http://hellas-songs.ru/en/singer/").get();
        }
        h = doc.toString();
        h1 = h.split("<div class=\"pslist mainborder\"");
        String[] h2 = h1[1].split("<div class=\"pagination");
        String[] h3 = h2[0].split("<a href=\"http://hellas-songs.ru/en/person/");
        for (int i = 1; i < h3.length; i += 2) {
            String[] h4 = h3[i].split("/");
            for (int j = 0; j < h4.length; j = j + 5) {
                Integer converzija = 0;
                AL.add(converzija.parseInt(h4[j].trim()));
            }
            String[] h5 = h3[i].split("/" + "\">");
            for (int j = 1; j < h5.length; j += 5) {
                String[] h6 = h5[j].split("</a>");
                for (int k = 0; k < h6.length; k += 2) {
                    authorNames.add(h6[k]);
                }
            }
        }
        Integer[] authorIDs = new Integer[AL.size()];
        AL.toArray(authorIDs);
        author.setSongList(authorIDs);
        author.setName(authorNames);
        return author;
    } //Parsiranje autora is html-a

    public static Songs getSongs(Integer integer) throws IOException {
        ArrayList songNames = new ArrayList();
        ArrayList AL = new ArrayList();
        Document doc;
        String h;
        String[] h1;
        Songs songs = new Songs();
        doc = Jsoup.connect("http://hellas-songs.ru/en/person/" + integer + '/').get();
        h = doc.toString();
        if (h.contains("big_blue\">\n" +
                "      Songs")) {

            h1 = h.split("big_blue\">\n" +
                    "      Songs");
            String[] h2 = h1[1].split("   </div> \n" +
                    "   <!--/content--> ");
            String[] h3 = h2[0].split("<a href=\"http://hellas-songs.ru/en/song/");
            for (int i = 1; i < h3.length; i++) {
                String[] h4 = h3[i].split("/");
                for (int j = 0; j < h4.length; j += 8) {
                    Integer converzija = 0;
                    if (!h4[j].contains("div")) {
                        AL.add(converzija.parseInt(h4[j].trim()));
                    }
                }
                String[] h5 = h3[i].split("/" + "\">");
                for (int j = 1; j < h5.length; j += 2) {
                    String[] h6 = h5[j].split("</a>");
                    songNames.add(h6[0]);
                }
            }

            Integer[] songIDs = new Integer[AL.size()];
            AL.toArray(songIDs);
            songs.setSongNames(songNames);
            songs.setSongIDs(songIDs);
        } else {
            AL.add(-1);
            songNames.add("@@@");
            Integer[] songIDs = new Integer[AL.size()];
            AL.toArray(songIDs);
            songs.setSongNames(songNames);
            songs.setSongIDs(songIDs);
        }
        return songs;
    }//Parsiranje pesama iz html-a


}
