package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.service.History;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Solyk on 26.01.2017.
 */
public class MainController {

    private View consoleJDBC;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.consoleJDBC = view;
        this.manager = manager;
    }

    public void run(){
//
//        connectToDb();
//
//        while (true) {
//            consoleJDBC.write("Введи команду (или help для помощи):");
//            String command = consoleJDBC.read();
//
//            if (command.equals("list")) {
//                doList();
//            } else if (command.equals("help")) {
//                doHelp();
//            } else if (command.equals("tableslist")) {
//                tablesList();
//            } else if (command.equals("columnslist|")) {
//                columnsList(command);
//            } else if (command.startsWith("coltype|")) {
//
//                columnType(command);
//            }else if (command.equals("exit")) {
//                consoleJDBC.write("До скорой встречи!");
//                System.exit(0);
//            } else if (command.startsWith("find|")) {
//                doFind(command);
//            } else if (command.equals("history")) {
//                printHistory();
//            }else {
//                consoleJDBC.write("Несуществующая команда: " + command);
//            }
//        }
    }

    private void connectToDb() {

        consoleJDBC.write("\t\t\t\t\t\t\t\tВас приветствует приложение SqlCMD");
        consoleJDBC.write("Пожалуйста, введите данные для подключения к базе данных в формате: username|password");

        while (true) {
            try {
                String string = consoleJDBC.read();
                String[] data = string.split("\\|");
                if (data.length != 2) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 2, но есть: " + data.length);
                }
                String userName = data[0];
                String password = data[1];

                manager.connect(userName, password);
                break;
            } catch (Exception e) {
                consoleJDBC.write(History.getDate() + " " + "Не удалось подключиться к базе данных " + e.getMessage());
                History.cache.add(History.getDate() + " " + "Не удалось подключиться к базе данных " + e.getMessage());
            }
        }

        consoleJDBC.write("\t\t\t\t\t\t\t\tУспех, вы подключились к базе данных:");
        consoleJDBC.write("Oracle Database 10g Express Edition Release 10.2.0.1.0 - Production");

        History.cache.add(History.getDate() + " " + "Вы подключились к базе данных: Oracle Database 10g Express Edition Release 10.2.0.1.0 - Production");
    }

//    private void tablesList(){
//
//        ArrayList<String> tableList = manager.getAllTableNames();
//
//        if(tableList != null) {
//            consoleJDBC.write(tableList.toString());
//        }
//
//    }
//
//    private void columnsList(String tableName){
//
//       ArrayList<String> columnsName =  manager.getAllColumnNamesFromTable(tableName);
//
//       if (columnsName != null){
//           consoleJDBC.write(columnsName.toString());
//       }
//    }
//
//    private void columnType(String tableName, String columnName){
//
//        ArrayList<String> columnType = manager.getDataTypeColumnFromTable(tableName, columnName);
//
//        if(columnName != null){
//            consoleJDBC.write(columnType.toString());
//        }
//    }
//
//    private static void printHistory() {
//        for (int i = 0; i < History.cache.size(); i++){
//            System.out.println(History.cache.get(i));
//            System.out.println();
//        }
//    }


}
