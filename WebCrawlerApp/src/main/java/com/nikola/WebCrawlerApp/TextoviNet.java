package com.nikola.WebCrawlerApp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

import static com.nikola.WebCrawlerApp.Generate.createDirectory;
import static com.nikola.WebCrawlerApp.Generate.createTxt;

public class TextoviNet {
    public static boolean fromBegining=true;
    public static Character[] slova ;

    public static void onInit(){
        WebCrawler w = new WebCrawler();
        Language l = new Language();
    }


    public static void crawl() throws IOException {
        onInit();
        long startTime = System.currentTimeMillis();
        Integer[] izvodjaci;

        int brojacP = 1;
        int brojacIz = 0;

        if(fromBegining==true){
            slova = fromAZ();
        }
        createDirectory("TextoviNet");
        for (Character c : slova) {
            if (c == '@') {
                createDirectory("TextoviNet/0-9");

            } else {
                createDirectory("TextoviNet/" + c.toString());
            }
            Author temp = idIzvodjacaPoKarakteru(c);
            izvodjaci = temp.getSongList();
            for (int i =0; i < izvodjaci.length;i++){
                String author = temp.getAuthor(i);
                if (c == '@') {
                    createDirectory("TextoviNet/0-9/" + author);

                } else {
                    createDirectory("TextoviNet/"+c + "/" + author);
                }
                Songs temp2 = idPesmeIzvodjaca(izvodjaci[i]);
                Integer[] songIDs = temp2.getSongIDs();

                brojacIz++;
                for (int j=0; j< songIDs.length;j++){
                    String songName;
                    String url = "http://www.tekstovi.net/2,"+izvodjaci[i]+","+songIDs[j]+".html";
                    String songNameRaw =temp2.getSongName(j);

                    if(songNameRaw.contains("/")){
                        String newSongName = songNameRaw.replace('/',' ');
                        songName = author + " - " +newSongName;
                    }else{
                        songName = author + " - " +songNameRaw;
                    }
                    if (c == '@') {
                        createTxt(url,"TextoviNet/0-9/" + author +'/'+ songName,0);

                    } else {
                        createTxt(url,"TextoviNet/"+c + "/" + author+"/" + songName,0);
                    }

                    System.out.println("author " + izvodjaci[i] + " song" + songIDs[j]);
                    System.out.print("Crawl done, number of authors : " + brojacIz);
                    System.out.println(" number of songs : " + brojacP);
                    brojacP++;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.print("Crawl done, number of authors : " + brojacIz);
        System.out.println(" number of songs : " + brojacP);
        System.out.println(totalTime + " milisecunds taken :)");
    }

    public static String getAuthorName(String url) throws IOException {
        String name = "";
        String h;
        String[] temp = new String[10];
        WebCrawler w = new WebCrawler();
        name = w.getUrlSource(url);
        temp = name.split("lyricCaptB\">");
        name = temp[1];
        temp = name.split("</");
        name = temp[0];
        return name;
    }
    public static String getSongName(String url) throws IOException {
        String name = "";
        String[] temp = new String[10];
        WebCrawler w = new WebCrawler();
        name = w.getUrlSource(url);
        temp = name.split("<h3 class=\"lyricCapt\">");
        name = temp[1];
        temp = name.split("</");
        name = temp[0];
        return name;

    }
    ///Indexing
    public static Character[] fromAZ() {//52387  //'@', 'A', 'B', 'C',
        //od A do D = 12470
        //od D do H ~ 10294 'D','E', 'F', 'G',
        //od H do L ~ 8862 'H','I', 'J', 'K',
        //od L do N ~ 16111 ,'N', 'O','P', 'Q', 'R', 'S', 'T', 'U', 'V' 'L', 'M',
        // od N do V
        //od V do Z 2958
        Character[] c = {'@', 'A', 'B', 'C','D','E', 'F','G','H','I', 'J', 'K','L','M' ,'N', 'O','P', 'Q', 'R','S', 'T',
                'U', 'V', 'W','X', 'Y', 'Z'};
        return c;
    }
    public static void fromSpecificLetter(char letter) {

        char[] c = {'@', 'A', 'B', 'C','D','E', 'F','G','H','I', 'J', 'K','L','M' ,'N', 'O','P', 'Q', 'R','S', 'T',
                'U', 'V', 'W','X', 'Y', 'Z'};
        boolean hit=false;

        char [] temp = new char[27];
        int j=0;
        for (int i = 0; i < c.length;i++){
            if(c[i] == letter || hit==true){
                hit = true;
                temp[j] = c[i];
                j++;
            }
        }
        ArrayList list = new ArrayList();
        String string="";
        for (char c1 : temp){
            if(c1 > 64 && c1 <91) {
                list.add(c1);
            }
        }
        Character[] newArray = new Character[list.size()];
        list.toArray(newArray);
        for (int i =0; i<newArray.length;i++){
        }
        slova= newArray;
        fromBegining=false;
    }

    public static Songs idPesmeIzvodjaca(Integer x) throws IOException {
        Document doc;
        Songs songs = new Songs();
        String h;
        ArrayList AL = new ArrayList(100000);
        ArrayList songNames = new ArrayList(100000);
        WebCrawler w = new WebCrawler();

        doc = Jsoup.connect("http://www.tekstovi.net/2,"+x+",0.html").get();
        h = doc.select("a").toString();
        String regex = "<a href=\"2," + x.toString() + ",";
        String[] h1 = h.split(regex);
        for(int j = 1; j<h1.length;j++){
            String h2[] = h1[j].split(".html\">");
            for (String e2 : h2) {
                int i = 0;
                Integer converzija = 0;

                if (e2.length() < 6) {
                    if(e2.startsWith("1") || e2.startsWith("1") || e2.startsWith("2") || e2.startsWith("3") || e2.startsWith("4") || e2.startsWith("5") || e2.startsWith("6") || e2.startsWith("7") || e2.startsWith("8") || e2.startsWith("9") || e2.startsWith("0")){
                        AL.add(converzija.parseInt(e2));
                        i++;
                    }
                }
                if (i<1){
                    String[] h3 = e2.split("<"+'/'+"a>");
                    String songName=h3[0];
                    char[] forbiden = {'.','?','[',']','*','\\',':','"','/',';','=','\'','|'};
                    for (Character c: forbiden){
                        if(h3[0].contains(c.toString())){
                            songName = h3[0].replace(c," ".toCharArray()[0]);
                        }
                    }
                    if (h3[0].contains("&amp;")){
                        songName = h3[0].replaceFirst("&amp","");
                    }

                    if(h3[0].equals(songName)){
                        songNames.add(h3[0]);
                    }else{
                        songNames.add(songName);
                    }
                    i = 0;
                }
            }
        }


        Integer[] idPesama = new Integer[AL.size()];
        AL.toArray(idPesama);
        songs.setSongIDs(idPesama);
        songs.setSongNames(songNames);
        return songs;

    }
    public static Author idIzvodjacaPoKarakteru(char x) throws IOException {
        Author authors = new Author();
        Document doc;
        String h;
        ArrayList songIDs = new ArrayList(100000);
        ArrayList songAuthor = new ArrayList(100000);

        doc = Jsoup.connect("http://www.tekstovi.net/2,"+x+",0.html").get();
        h = doc.select("b").toString();
        String[] h1 = h.split("<b><a href=\"/diskusije/");
        String[] h2 = h1[0].split("<b><a href=\"2,");
        for (String e : h2) {
            String[] h3 = e.split(",0.html"+'"');
            for (int j = 1; j <h3.length;j++){
                int i = 0;
                Integer converzija = 0;

                if (h3[i].charAt(0) > 47 && h3[i].charAt(0) < 58) {
                    songIDs.add(converzija.parseInt(h3[i]));
                    i++;
                }
            }
            try{
                String[] h4 = e.split(",0.html"+'"'+">");
                String[] h5 = h4[1].split("<"+'/'+"a>");
                String name=h5[0];
                char[] forbiden = {'.','?','[',']','*','\\',':','"','/',';','=','\'','|'};
                for (Character c: forbiden){
                    if(h5[0].contains(c.toString())){
                        name = h5[0].replace(c," ".toCharArray()[0]);
                    }
                }
                if (h5[0].contains("&amp;")){
                    name = h5[0].replaceFirst("&amp","");
                }

                if(h5[0].equals(name)){
                    songAuthor.add(h5[0]);
                }else{
                    songAuthor.add(name);
                }
            }catch (ArrayIndexOutOfBoundsException exe){
            }
        }

        Integer[] nizIzvodjaca = new Integer[songIDs.size()];
        songIDs.toArray(nizIzvodjaca);




        authors.setName(songAuthor);
        authors.setSongList(nizIzvodjaca);

        return authors;
    }




}
