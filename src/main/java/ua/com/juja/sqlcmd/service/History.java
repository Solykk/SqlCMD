package ua.com.juja.sqlcmd.service;

/**
 * Created by Solyk on 29.01.2017.
 */
public class History {

    private String cache;

    public History(){
        this.cache = "";
    }

    public void historyAdd(String text){
        cache += text;
    }

    public String getCache() {
        return cache;
    }
}
