package com.nikola.WebCrawlerApp;

import java.util.ArrayList;

/**
 * Created by nikola3in1 on 2.1.17..
 */
public class Author {
    private ArrayList lista;
    private Integer[] authorID;
    private ArrayList authorName;

    public Author(ArrayList name,Integer[] authorID) {
        this.authorName=name;
        this.authorID=authorID;
    }
    public Author() {
    }

    public String getAuthor(int i){
        return authorName.get(i).toString();
    }

    public Integer[] getSongList() {
        return authorID;
    }

    public void setSongList(Integer[] songList) {
        this.authorID = songList;
    }

    public ArrayList getName() {
        return authorName;
    }

    public void setName(ArrayList name) {
        this.authorName = name;
    }

}
