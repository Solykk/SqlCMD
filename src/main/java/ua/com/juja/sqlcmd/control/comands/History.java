package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.view.View;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by Solyk on 29.01.2017.
 */
public class History implements Command {

    public static ArrayList<String> cache = new ArrayList<>();
    private View view;

    public History(View view) {
        this.view = view;
    }

    public static String getDate(){

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        return new SimpleDateFormat("yyyy,MM,dd_(HH:mm:ss)").format(date);
    }


    @Override
    public boolean isProcessed(String command) {
        return  command.equals("history");
    }

    @Override
    public void process(String command) {
        for (int i = 0; i < History.cache.size(); i++){
            view.write(History.cache.get(i));
        }
    }
}
