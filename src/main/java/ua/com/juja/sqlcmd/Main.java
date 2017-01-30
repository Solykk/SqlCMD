package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.control.MainController;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Main {
    public static void main(String[] args) {

        View view = new Console();
        JDBCDatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();

        manager.connect("hr", "hr");

        ArrayList<String> tables = manager.getAllTableNames();

        for (int i = 0; i < tables.size(); i++){
            System.out.println(tables.get(i));
        }

        ArrayList<String> tables2 = manager.getAllColumnNamesFromTable("COUNTRIES");

        for (int i = 0; i < tables2.size(); i++){
            System.out.println(tables2.get(i));
        }

        ArrayList<String> cache =  manager.getHistory().getCache();

        for (int i = 0; i < cache.size(); i++){
            System.out.println(cache.get(i));
        }


    }
}
