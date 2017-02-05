package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.control.MainController;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.control.comands.Help;
import ua.com.juja.sqlcmd.control.comands.History;
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



//        ArrayList<String> tables = manager.getAllTableNames();
//
//        for (int i = 0; i < tables.size(); i++){
//            System.out.println(tables.get(i));
//        }
//        System.out.println();

//        ArrayList<String> tables2 = manager.getAllColumnNamesFromTable("COUNTRIES");
//
//        for (int i = 0; i < tables2.size(); i++){
//            System.out.println(tables2.get(i));
//        }
//        System.out.println();

        String step = "Вы подключились к базе данных. Поздравляю )";

//        stepPrint(step);
        System.out.println();
        Console con = new Console();
        Help n = new Help(con);
        n.process("");


//
//        printTable(manager.readQuery("SELECT * FROM USER_TAB_COLUMNS"));
//        System.out.println();
//        printTable(manager.getAllTableNames());
//        System.out.println();
//        printTable(manager.getAllColumnNamesFromTable("EMPLOYEES"));
//        System.out.println();
//        printTable(manager.getDataTypeColumnFromTable("JOBS", "JOB_ID"));
//        System.out.println();


//        ArrayList<String> tables3 = manager.getDataTypeColumnFromTable("EMPLOYEES", "EMPLOYEE_ID");
//
//        for (int i = 0; i < tables3.size(); i++){
//            System.out.println(tables3.get(i));
//        }
//        System.out.println();

//        printTable(manager.getDataTypeAllColumnsFromTable("EMPLOYEES"));
//        System.out.println();
//
//        System.out.println();

//        printTable(manager.readTable("REGIONS"));
//        System.out.println();
//        printTable(manager.readTable("LOCATIONS"));
//        System.out.println();
//        printTable(manager.readTable("DEPARTMENTS"));
//        System.out.println();
//        printTable(manager.readTable("JOBS"));
//        System.out.println();
//        printTable(manager.readTable("EMPLOYEES"));
//        System.out.println();
//        printTable(manager.readTable("JOB_HISTORY"));
//        System.out.println();
//        printTable(manager.readTable("SERTEE"));
//        System.out.println();
//        printTable(manager.readTable("MYTABLE"));
//        System.out.println();
//        printTable(manager.readTable("SERTE"));
//        System.out.println();
//        printTable(manager.readTable("COUNTRIES"));



//        String [] first = new String[]{"SALARY", "17000"};
//        String [] second = new String[]{"DEPARTMENT_ID", "90"};
//        String [] third = new String[]{"JOB_ID", "'AD_VP'"};
//
//        ArrayList<String[]> uyt = new ArrayList<>();
//
//        uyt.add(first);
//        uyt.add(second);
//        uyt.add(third);
//
////        printTable(manager.read("EMPLOYEES", uyt));
//
//        System.out.println();
//
//        String [] first1 = new String[]{"ID", "2"};
//        String [] second1 = new String[]{"DESCRIPTION", "'RRRR'"};
//        String [] third1 = new String[]{"ID", "34"};
//        String [] forth1 = new String[]{"DESCRIPTION", "'TTTT'"};
//
//        ArrayList<String[]> uyt1 = new ArrayList<>();
//        uyt1.add(first1);
//        uyt1.add(second1);
//        ArrayList<String[]> uyt2 = new ArrayList<>();
//        uyt2.add(third1);
//        uyt2.add(forth1);
//
//        System.out.println(manager.update("SERTE",uyt2,uyt1));
//
//        ArrayList<String[]> testCreat = new ArrayList<>();
//        testCreat.add(new String[]{"ID", "45"});
//        testCreat.add(new String[]{"DESCRIPTION", "'TEST'"});

//       Long id =  manager.create("SERT", testCreat, true);
//        System.out.println(id);

//



    }


}
