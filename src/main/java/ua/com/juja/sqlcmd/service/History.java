package ua.com.juja.sqlcmd.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Solyk on 29.01.2017.
 */
public class History {

    public static ArrayList<String> cache = new ArrayList<>();

    public static String getDate(){

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        return new SimpleDateFormat("yyyy,MM,dd_(HH:mm:ss)").format(date);
    }
}
