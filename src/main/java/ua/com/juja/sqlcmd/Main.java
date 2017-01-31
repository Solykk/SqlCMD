package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.control.MainController;
import ua.com.juja.sqlcmd.model.Table;
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

        String step = "Вы подключились к базе данных. Поздравляю )";

        stepPrint(step);



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

      //  boolean g = manager.createTable("SERT",tables4, false);

//        System.out.println(g);

        for (int i = 0; i < tables4.size(); i++){
            System.out.println(tables4.get(i)[0] + " " + tables4.get(i)[1]);
        }
        System.out.println();

        printTable(manager.readTable("EMPLOYEES"));

        ArrayList<String[]> testCreat = new ArrayList<>();
        testCreat.add(new String[]{"ID", "45"});
        testCreat.add(new String[]{"DESCRIPTION", "'TEST'"});

//       Long id =  manager.create("SERT", testCreat, true);
//        System.out.println(id);


        for (int i = 0; i < History.cache.size(); i++){
            stepPrint(History.cache.get(i));
            System.out.println();
        }
        System.out.println();


    }


    private static void printTable(Table table){

        System.out.println(table.getTableDate().get(0).getValue().size());

        for (int i = 0; i < table.getTableDate().size(); i++){
            System.out.print(table.getTableDate().get(i).columnName() + "  ");

        }

        System.out.println();
        for (int i = 0; i < table.getTableDate().get(0).getValue().size(); i++){
             for (int j = 0; j < table.getTableDate().size(); j++) {
                 System.out.print(table.getTableDate().get(j).getValue().get(i) + " ");
             }
            System.out.println();
        }



    }

    private static void stepPrint(String step) {
        char [] rezz = step.toCharArray();

        for (int i = 0; i < rezz.length; i++){
//        if(rezz[i] != ' ') {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        }
            System.out.print(rezz[i]);

        }
    }
}
