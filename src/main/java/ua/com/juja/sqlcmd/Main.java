package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.control.MainController;
import ua.com.juja.sqlcmd.service.History;
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
        System.out.println();

        ArrayList<String> tables2 = manager.getAllColumnNamesFromTable("COUNTRIES");

        for (int i = 0; i < tables2.size(); i++){
            System.out.println(tables2.get(i));
        }
        System.out.println();

        ArrayList<String> tables3 = manager.getDataTypeColumnFromTable("EMPLOYEES", "EMPLOYEE_ID");

        for (int i = 0; i < tables3.size(); i++){
            System.out.println(tables3.get(i));
        }
        System.out.println();

        ArrayList<String[]> tables4 = manager.getDataTypeAllColumnsFromTable("EMPLOYEES");

        for (int i = 0; i < tables4.size(); i++){
            System.out.println(tables4.get(i)[0] + " " + tables4.get(i)[1]);
        }
        System.out.println();

        boolean g = manager.createTable("dd",tables4, false);

        System.out.println(g);

        for (int i = 0; i < tables4.size(); i++){
            System.out.println(tables4.get(i)[0] + " " + tables4.get(i)[1]);
        }
        System.out.println();


        for (int i = 0; i < History.cache.size(); i++){
            System.out.println(History.cache.get(i));
        }
        System.out.println();


    }
}
