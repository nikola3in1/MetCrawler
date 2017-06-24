package com.nikola.WebCrawlerApp;

import java.util.ArrayList;

public class Songs {
    private Integer[] songIDs;
    private ArrayList songNames;

    public Songs() {
    }

    public Songs(Integer[] songIDs, ArrayList songNames) {

        this.songIDs = songIDs;
        this.songNames = songNames;
    }

    public Integer[] getSongIDs() {
        return songIDs;
    }
    public String getSongName(int i){
        return songNames.get(i).toString();
    }
    public void setSongIDs(Integer[] songIDs) {
        this.songIDs = songIDs;
    }

    public ArrayList getSongNames() {
        return songNames;
    }

    public void setSongNames(ArrayList songNames) {
        this.songNames = songNames;
    }
}
