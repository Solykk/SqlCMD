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

        manager.connect("notebook", "notebook");

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
//
//        printTable(manager.readQuery("SELECT * FROM USER_TAB_COLUMNS"));
//        System.out.println();
        printTable(manager.getAllTableNames());
        System.out.println();
        printTable(manager.getAllColumnNamesFromTable("EMPLOYEES"));
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
        printHistory();


    }
//
    private static void printHistory() {
        for (int i = 0; i < History.cache.size(); i++){
            System.out.println(History.cache.get(i));

        }
    }


    private static void printTable(Table table){

        if(table == null){
            System.out.println("BREAK");
            return;
        }


        char simbol = '\u254F';
        ArrayList<Integer> maxLengs = new ArrayList<>();
        int sumOfLenght = 0;


        for (int j = 0; j < table.getTableDate().size(); j++) {

            String obj;
            if(table.getTableDate().get(j).getValue().size() == 0){
                obj = "";
            } else {
                obj = table.getTableDate().get(j).getValue().get(0);
                if (obj == null){
                    obj = "null";
                }
            }



            Integer tmp = obj.length();
            Integer tmpCol = table.getTableDate().get(j).columnName().length();

            for (int i = 0; i < table.getTableDate().get(j).getValue().size(); i++) {

                String tempObj = table.getTableDate().get(j).getValue().get(i);
                if (tempObj == null){
                    tempObj = "null";
                }

                Integer tempIns = tempObj.length();
                if (tmp < tempIns) {
                    tmp = tempIns;
                }
            }

            if(tmpCol > tmp){
                tmp = tmpCol;
            }

            maxLengs.add(tmp);

            sumOfLenght += tmp + 3;
        }


        char proba = '\u250C';
        char [] line = new char[sumOfLenght + 1];
        for (int q = 0; q < line.length; q++) {
                line[q] = '-';
        }

        char [] reBildName = new char[sumOfLenght];
        char [] strReBildName = table.getTableName().toCharArray();

        for (int j = 0; j < reBildName.length - 1; j++) {
                reBildName [j] = ' ';
            }

        if(strReBildName.length > reBildName.length){
            reBildName = strReBildName;
        } else {
            int fromToName = (reBildName.length - strReBildName.length) / 2;
            for (int j = fromToName, m = 0; m < strReBildName.length; j++, m++) {
                reBildName[j] = strReBildName[m];
            }
        }

        System.out.println(new String(line));
        System.out.print('|' + new String(reBildName) + '|');
        System.out.println();
        System.out.println(new String(line));

        sleeper(5);

        for (int i = 0; i < table.getTableDate().size(); i++){

            String bil = table.getTableDate().get(i).columnName();
            char [] reBild = new char[maxLengs.get(i) + 2];
            char [] strReBild = bil.toCharArray();

            for (int j = 0; j < reBild.length; j++) {
                reBild [j] = ' ';
            }

            int fromTo = (reBild.length - strReBild.length)/2;

            for (int j = fromTo, m = 0; m < strReBild.length; j++, m++) {
                reBild[j] = strReBild[m];
            }

            if(i == table.getTableDate().size() - 1){
                System.out.print('|' + new String(reBild) + '|');
            } else {
                System.out.print('|' + new String(reBild));
            }


            sleeper(5);


        }
        System.out.println();
        System.out.println(new String(line));

        for (int i = 0; i < table.getTableDate().get(0).getValue().size(); i++){

             for (int j = 0; j < table.getTableDate().size(); j++) {

                 String bil = table.getTableDate().get(j).getValue().get(i);

                 if(bil == null){
                     bil = "null";
                 }

                 char [] reBild = new char[maxLengs.get(j) + 2];
                 char [] strReBild = bil.toCharArray();

                 for (int w = 0; w < reBild.length; w++) {
                     reBild [w] = ' ';
                 }

                 int fromTo = (reBild.length - strReBild.length)/2;

                 for (int f = fromTo, m = 0; m < strReBild.length; f++, m++) {
                     reBild[f] = strReBild[m];
                 }

                 if(j == table.getTableDate().size() - 1){
                     System.out.print('|' + new String(reBild) + '|');
                 } else{
                     System.out.print('|' + new String(reBild));
                 }

                 sleeper(5);

             }
            System.out.println();
        }

        System.out.println(new String(line));
    }

    private static void sleeper(int time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void stepPrint(String step) {

        char [] rezz = step.toCharArray();

        for (int i = 0; i < rezz.length; i++){
            sleeper(100);
            System.out.print(rezz[i]);
        }
    }
}
