package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.service.History;
import ua.com.juja.sqlcmd.view.Console;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Solyk on 26.01.2017.
 */
public class  JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url = "jdbc:oracle:thin:/@localhost:1521:XE";

    public History getHistory() {
        return history;
    }

    private History history;
    private Console consoleJDBC;



    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        this.connection = null;
        this.history = new History();
        this.consoleJDBC = new Console();
    }

    public boolean connect(String userName, String dbPassword){
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            throw new RuntimeException("Please add jdbc jar to project.", e);
        }

        try {

            connection = DriverManager.getConnection(url, userName, dbPassword);

            consoleJDBC.write("\t\t\t\t\t\t\t\tУспех, вы подключились к базе ");
            consoleJDBC.write(connection.getMetaData().getDatabaseProductVersion());

            history.historyAdd("Вы подключились к базе");

            return true;

        } catch (SQLException e){

            connection = null;
            new Console().write("Неудача, не удалось подключиться к базе ");

            return false;
        }

    }

    @Override
    public ArrayList<String> getAllTableNames() {

        ArrayList<String> allTableNames = new ArrayList<>();
        String findAllTables = "SELECT TABLE_NAME FROM user_tables";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findAllTables))
        {


            while (resultSet.next()){
                String string = resultSet.getString("TABLE_NAME");
                allTableNames.add(string);

            }

            history.historyAdd("Вывод всех таблиц");

        } catch (SQLException | NullPointerException e){

            history.historyAdd("Ошибка. Не могу осуществить вывод всех таблиц");

            return null;
        }


        return allTableNames;
    }

    @Override
    public ArrayList<String> getAllColumnNamesFromTable(String tableName) {

        ArrayList<String> dateFromTable = new ArrayList<>();
        String dateTables = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = " + "'" + tableName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(dateTables))
        {


            while (resultSet.next()){
                String string = resultSet.getString("COLUMN_NAME");
                dateFromTable .add(string);

            }

            history.historyAdd("Вывод содержимого таблицы: " + tableName);

        } catch (SQLException e ){

            history.historyAdd("Ошибка. Не могу осуществить вывод содержимого таблицы " + tableName);

            return null;
        }


        return dateFromTable ;
    }


}

