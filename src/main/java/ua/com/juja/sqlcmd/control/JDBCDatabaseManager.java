package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.ColumnDate;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.service.History;
import ua.com.juja.sqlcmd.view.Console;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

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

    @Override
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
    public Table getAllTableNames() {

        ArrayList<ColumnDate> columnDatas = new ArrayList<>();
        columnDatas.add(new ColumnDate("TABLE_NAME", new ArrayList<String>()));

        String findAllTables = "SELECT TABLE_NAME FROM user_tables";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(findAllTables))
        {

            while (resultSet.next()){
                String string = resultSet.getString("TABLE_NAME");
                columnDatas.get(0).getValue().add(string);
            }

            History.cache.add(History.getDate() + " " + "Вывод всех таблиц");

            return new Table("ALL_TABLES", columnDatas);

        } catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу осуществить вывод всех таблиц" + "  " + e.getMessage());

            return null;
        }



    }

    @Override
    public Table getAllColumnNamesFromTable(String tableName) {

        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<String>()));

        String dateTables = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = " + "'" + tableName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(dateTables))
        {

            while (resultSet.next()){

                String string = resultSet.getString("COLUMN_NAME");
                columnDates.get(0).getValue().add(string);

            }

            History.cache.add(History.getDate() + " " + "Вывод содержимого таблицы: " + tableName);

            return new Table(tableName, columnDates);

        } catch (SQLException | NullPointerException e ){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу осуществить вывод содержимого таблицы " + tableName + "  " + e.getMessage());

            return null;
        }


    }

    @Override
    public Table getDataTypeColumnFromTable(String tableName, String columnName) {

        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<String>()));
        columnDates.add(new ColumnDate("DATA_TYPE", new ArrayList<String>()));
        columnDates.add(new ColumnDate("NULLABLE", new ArrayList<String>()));

        String columnVCtypeQuery = "SELECT COLUMN_NAME , data_type, DATA_LENGTH, NULLABLE FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "' AND COLUMN_NAME = " + "'" + columnName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(columnVCtypeQuery))
        {

            resultSetGetHelper(columnDates, resultSet);

            History.cache.add(History.getDate() + " " + "Определен тип данных содержащийся в таблице: " + tableName + " у колонки " + columnName);
            return new Table(tableName, columnDates);
        } catch (SQLException | NullPointerException e ){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу определить тип данных в таблице: " + tableName + " у колонки " + columnName  + "  " + e.getMessage());

            return null;
        }




    }

    @Override
    public Table getDataTypeAllColumnsFromTable(String tableName) {

        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<String>()));
        columnDates.add(new ColumnDate("DATA_TYPE", new ArrayList<String>()));
        columnDates.add(new ColumnDate("NULLABLE", new ArrayList<String>()));

        String columnVCtypeQueryAll = "SELECT COLUMN_NAME , data_type, DATA_LENGTH, NULLABLE FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(columnVCtypeQueryAll))
        {

            resultSetGetHelper(columnDates, resultSet);

            History.cache.add(History.getDate() + " " + "Определен тип данных содержащийся в таблице: " + tableName);
            return new Table(tableName, columnDates) ;
        } catch (SQLException | NullPointerException e ){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу определить тип данных в таблице: " + tableName + "  " + e.getMessage());

            return null;
        }



    }

    @Override
    public boolean createTableWithPK (String tableName, ArrayList<String> settings, String columnNamePK, Long startWith) {

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
    public boolean createTableWithoutPK(String tableName, ArrayList<String> settings) {

        String ulrSettings = "";
        for (int i = 0; i < settings.size() ; i++) {
            if(i == settings.size() - 1) {
                ulrSettings += settings.get(i);
            } else {
                ulrSettings += settings.get(i) + ", ";
            }
        }
        String urlTableCreate = "CREATE TABLE " + tableName + " (" + ulrSettings + " )";

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate(urlTableCreate);

            History.cache.add(History.getDate() + " " + "Создал таблицу: " + tableName);

            return true;

        }  catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не могу создать таблицу: " + tableName + "  " + e.getMessage());

            return false;
        }
    }

    @Override
    public boolean insert(String tableName, ArrayList<String[]> columnNameVSdata, boolean idKey) {

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

        ArrayList<ColumnDate> columnDates = getColumnDates(tableName);

        String query = "SELECT * FROM " + tableName;

        return getTableHelper(tableName, columnDates, query);

    }

    @Override
    public Table read(String tableName, ArrayList<String[]> settings) {

        ArrayList<ColumnDate> columnDates = getColumnDates(tableName);

        String queryPost = generateQueryAndString(settings);

        String query = "SELECT * FROM " + tableName +  " WHERE " + queryPost;

        return getTableHelper(tableName, columnDates, query);

    }

    @Override
    public boolean update(String tableName, ArrayList<String[]> settingsForUpdate, ArrayList<String[]> settingsHowUpdate) {

        String ulrPrePost = generateQueryComaString(settingsForUpdate);
        String ulrPost = generateQueryAndString(settingsHowUpdate);

        String ulr  = "UPDATE " + tableName +  " SET " + ulrPrePost + " WHERE " + ulrPost;

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate(ulr);

            History.cache.add(History.getDate() + " " + "Вы успешно обновили данные в таблицу: " + tableName);

            return true;

        } catch (SQLException | NullPointerException e) {

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось обновить данные в таблице: " + tableName + "  " + e.getMessage());

            return false;

        }

    }

    @Override
    public boolean drop(String tableName) {

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate("DROP TABLE " + tableName);

            History.cache.add(History.getDate() + " " + "Вы успешно удалии таблицу: " + tableName);

            return true;

        } catch (SQLException | NullPointerException e) {

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось удолить таблицу: " + tableName + "  " + e.getMessage());

            return false;

        }

    }

    @Override
    public boolean delete(String tableName, ArrayList<String[]> settings) {

        String sqlPost = generateQueryAndString(settings);

        String sqlQuery = "DELETE FROM " +  tableName + " WHERE " + sqlPost;

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate(sqlQuery);

            History.cache.add(History.getDate() + " " + "Вы успешно удалили запись в таблице: " + tableName);

            return true;

        } catch (SQLException | NullPointerException e) {

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось удалить запись из таблицы: " + tableName + "  " + e.getMessage());

            return false;

        }


    }

    @Override
    public boolean clear(String tableName) {

        try (Statement statement = connection.createStatement())

        {
            statement.executeUpdate("DELETE " + tableName);


            History.cache.add(History.getDate() + " " + "Вы успешно Очистили таблицу: " + tableName);

            return true;

        } catch (SQLException | NullPointerException e) {

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось очистить таблицу: " + tableName + "  " + e.getMessage());

            return false;

        }

    }

    @Override
    public boolean cudQuery(String query) {

        try (Statement statement =  connection.createStatement())
        {
            statement.executeUpdate(query);
            System.out.println("Успех");
            return true;
        } catch (SQLException | NullPointerException e) {
            System.out.println("Неудача");
            return false;
        }

    }

    @Override
    public Table readQuery(String query) {


        try (Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {

            ArrayList<ColumnDate> columnDates = new ArrayList<>();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int lengthOfCol = rsMetaData.getColumnCount();

            for (int i = 0; i < lengthOfCol; i++) {
                String columnName = rsMetaData.getColumnName(i + 1);
                columnDates.add(new ColumnDate(columnName, new ArrayList<String>()));
            }

            columnSortHelper(columnDates, resultSet);

            System.out.println("Успех");
            return new Table( "Your Query", columnDates);

        } catch (SQLException | NullPointerException e) {
            System.out.println("Неудача + " + e.getMessage());
            return null;
        }

    }

    private String generateQueryComaString(ArrayList<String[]> settingsForUpdate) {

        String ulrPost = "";

        for (int i = 0; i < settingsForUpdate.size() ; i++) {
            ulrPost += settingsForUpdate.get(i)[0] + " = " + settingsForUpdate.get(i)[1];

            if (i < settingsForUpdate.size() - 1){
                ulrPost += ", ";
            }
        }
        return ulrPost;
    }

    private String generateQueryAndString(ArrayList<String[]> settings) {

        String queryPost = "";

        for (int i = 0; i < settings.size() ; i++) {
            queryPost += settings.get(i)[0] + " = " + settings.get(i)[1];

            if (i < settings.size() - 1){
                queryPost += " AND ";
            }
        }
        return queryPost;
    }

    private Table getTableHelper(String tableName, ArrayList<ColumnDate> columnDates, String query) {

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {
            columnSortHelper(columnDates, resultSet);

            return new Table(tableName, columnDates);

        } catch (SQLException | NullPointerException e){

            History.cache.add(History.getDate() + " " + "Ошибка. Не получилось прочесть таблицу: " + tableName + "  " + e.getMessage());

            return null;

        }
    }

    private void columnSortHelper(ArrayList<ColumnDate> columnDates, ResultSet resultSet) throws SQLException, NullPointerException {

        while (resultSet.next()){

            for(int index = 0; index < columnDates.size(); index++){

                String columnName = columnDates.get(index).columnName();
                String temp = resultSet.getString(columnName);

                columnDates.get(index).getValue().add(temp);
            }

        }
    }

    private void resultSetGetHelper(ArrayList<ColumnDate> columnDates, ResultSet resultSet) throws SQLException, NullPointerException {
        while (resultSet.next()){

            String stringName = resultSet.getString("COLUMN_NAME");
            columnDates.get(0).getValue().add(stringName);
            String stringType = resultSet.getString("DATA_TYPE");
            String stringLength = resultSet.getString("DATA_LENGTH");
            String cotcat = stringType + "(" + stringLength + ")";
            columnDates.get(1).getValue().add(cotcat);
            String stringNullable = resultSet.getString("NULLABLE");
            columnDates.get(2).getValue().add(stringNullable);

        }
    }

    private ArrayList<ColumnDate> getColumnDates(String tableName) {

        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        ArrayList<String> columnNamesFromTable = getAllColumnNamesFromTable(tableName).getTableDate().get(0).getValue();

        for (int i = 0; i < columnNamesFromTable.size(); i++) {
            ColumnDate temp = new ColumnDate(columnNamesFromTable.get(i), new ArrayList<String>());
            columnDates.add(temp);
        }
        return columnDates;
    }

}

