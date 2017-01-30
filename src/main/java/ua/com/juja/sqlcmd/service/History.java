package ua.com.juja.sqlcmd.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solyk on 29.01.2017.
 */
public class History {

    private ArrayList<String> cache;

    public History(){
        this.cache = new ArrayList<>();
    }

    public void historyAdd(String text){
        cache.add(text);
    }

    public ArrayList<String> getCache() {
        return cache;
    }
}
