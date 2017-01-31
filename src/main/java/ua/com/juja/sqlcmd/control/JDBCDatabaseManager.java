package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.ColumnDate;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.service.History;
import ua.com.juja.sqlcmd.view.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Solyk on 26.01.2017.
 */
public class  JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url = "jdbc:oracle:thin:/@localhost:1521:XE";
    private Console consoleJDBC;



    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        this.connection = null;
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

            History.cache.add(History.getDate() + " " + "Вы подключились к базе");

            return true;

        } catch (SQLException e){

            connection = null;
            new Console().write(History.getDate() + " " + "Неудача, не удалось подключиться к базе ");

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

            History.cache.add(History.getDate() + " " + "Вывод всех таблиц");

        } catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу осуществить вывод всех таблиц" + "  " + e.getMessage());

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

            History.cache.add(History.getDate() + " " + "Вывод содержимого таблицы: " + tableName);

        } catch (SQLException e ){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу осуществить вывод содержимого таблицы " + tableName + "  " + e.getMessage());

            return null;
        }


        return dateFromTable ;
    }

    @Override
    public ArrayList<String> getDataTypeColumnFromTable(String tableName, String columnName) {

        ArrayList<String> columnVCtype = new ArrayList<>();
        columnVCtype.add(tableName);

        String columnVCtypeQuery = "SELECT COLUMN_NAME , data_type FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "' AND COLUMN_NAME = " + "'" + columnName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(columnVCtypeQuery))
        {


            while (resultSet.next()){

                String stringName = resultSet.getString("COLUMN_NAME");
                columnVCtype .add(stringName);
                String stringType = resultSet.getString("data_type");
                columnVCtype .add(stringType);

            }


            History.cache.add(History.getDate() + " " + "Определен тип данных содержащийся в таблице: " + tableName + " у колонки " + columnName);

        } catch (SQLException e ){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу определить тип данных в таблице: " + tableName + " у колонки " + columnName  + "  " + e.getMessage());

            return null;
        }


        return columnVCtype;

    }


    @Override
    public ArrayList<String[]> getDataTypeAllColumnsFromTable(String tableName) {

        ArrayList<String[]> columnVCtypeAll = new ArrayList<>();


        String columnVCtypeQueryAll = "SELECT COLUMN_NAME , data_type FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(columnVCtypeQueryAll))
        {


            while (resultSet.next()){

                String[] columnAndtType = new String[2];

                String stringName = resultSet.getString("COLUMN_NAME");
                String stringType = resultSet.getString("data_type");

                columnAndtType[0] = stringName;
                columnAndtType[1] = stringType;

                columnVCtypeAll.add(columnAndtType);

            }

            History.cache.add(History.getDate() + " " + "Определен тип данных содержащийся в таблице: " + tableName);

        } catch (SQLException e ){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу определить тип данных в таблице: " + tableName + "  " + e.getMessage());

            return null;
        }


        return columnVCtypeAll ;
    }

    @Override
    public boolean createTableWithPK (String tableName, ArrayList<String[]> settings, String columnNamePK, Long startWith) {

        createTableWithoutPK(tableName, settings);

        String primaryKey = "ALTER TABLE " + tableName + " ADD (CONSTRAINT " +  tableName + "_pk PRIMARY KEY (" + columnNamePK + "))";

        String sequence = "CREATE SEQUENCE " + tableName + "_seq START WITH " + startWith;

        try (Statement statement = connection.createStatement())

        {

            statement.executeUpdate(primaryKey);
            statement.executeUpdate(sequence);

            History.cache.add(History.getDate() + " " + "Cоздал таблицу: " + tableName);

            return true;

        }  catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу создать таблицу: " + tableName + "  " + e.getMessage());

            return false;
        }


    }

    @Override
    public boolean createTableWithoutPK(String tableName, ArrayList<String[]> settings) {

        String urlTableCreate = "CREATE TABLE departments (ID NUMBER(10) NOT NULL, DESCRIPTION  VARCHAR2(50)  NOT NULL)";

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate(urlTableCreate);

            History.cache.add(History.getDate() + " " + "Cоздал таблицу: " + tableName);

            return true;

        }  catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу создать таблицу: " + tableName + "  " + e.getMessage());

            return false;
        }
    }

    @Override
    public boolean createData(String tableName, ArrayList<String[]> columnNameVSdata, boolean idKey) {

        String columnNames = "";
        String datas =  "";

        for (int index = 0; index < columnNameVSdata.size(); index++){
            columnNames += columnNameVSdata.get(index)[0];
            if(index != columnNameVSdata.size() - 1){
                columnNames += ", ";
            }
        }

        if(idKey) {
            for (int index = 0; index < columnNameVSdata.size(); index++) {
                if (index == 0) {
                    datas += tableName + "_SEQ.nextval, ";
                } else {
                    if (index != 0 && index != columnNameVSdata.size() - 1) {
                        datas += "', '";
                    }

                    datas += columnNameVSdata.get(index)[1];
                }
            }
        }
            if (!idKey) {
            for (int j = 0; j < columnNameVSdata.size(); j++){
                datas += columnNameVSdata.get(j)[1];

                if (j != columnNameVSdata.size() - 1) {
                    datas += ", ";
                }

            }
        }

        String url = "INSERT INTO " + tableName + "( " + columnNames + ") VALUES ( " + datas + " )";

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate(url);
            History.cache.add(History.getDate() + " " + "Вы успешно добавили данные в таблицу: " + tableName);
            return true;

        } catch (SQLException | NullPointerException e) {

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось добавить данные в таблицу: " + tableName + "  " + e.getMessage());

            return false;

        }
    }

    @Override
    public Table readTable(String tableName) {

        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        ArrayList<String> columnNamesFromTable = getAllColumnNamesFromTable(tableName);

        for (int i = 0; i < columnNamesFromTable.size(); i++) {
            ColumnDate temp = new ColumnDate(columnNamesFromTable.get(i), new ArrayList<String>());
            columnDates.add(temp);
        }

        String query = "SELECT * FROM " + tableName;

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {
            while (resultSet.next()){

                for(int index = 0; index < columnDates.size(); index++){

                    String columnName = columnDates.get(index).columnName();
                    String temp = resultSet.getString(columnName);

                    columnDates.get(index).getValue().add(temp);
                }

            }

            return new Table(tableName, columnDates);

        } catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось прочесть таблицу: " + tableName + "  " + e.getMessage());
            return null;
        }

    }

    @Override
    public Table read(String tableName, ArrayList<String[]> settings) {
        return null;
    }

    @Override
    public boolean update(String tableName, ArrayList<String[]> settings) {
        return false;
    }

    @Override
    public boolean delete(String tableName, ArrayList<String[]> settings) {
        return false;
    }

}

