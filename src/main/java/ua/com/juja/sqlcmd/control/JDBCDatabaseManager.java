package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.ColumnDate;
import ua.com.juja.sqlcmd.model.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class  JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url;

    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        this.connection = null;
        this.url = "jdbc:oracle:thin:/@localhost:1521:XE";
    }

    @Override
    public void connect(String userName, String dbPassword) throws SQLException{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }

        try {
            connection = DriverManager.getConnection(url, userName, dbPassword);
        } catch (SQLException e) {
            connection = null;
            throw e;
        }
    }

    @Override
    public Table getTableNames() throws SQLException, NullPointerException{

        ArrayList<ColumnDate> columnDatas = new ArrayList<>();
        columnDatas.add(new ColumnDate("TABLE_NAME", new ArrayList<>()));

        String query = "SELECT TABLE_NAME FROM user_tables";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query))
        {
            while (resultSet.next()){
                String data = resultSet.getString("TABLE_NAME");
                columnDatas.get(0).getValue().add(data);
            }

            return new Table("ALL_TABLES", columnDatas);
        }
    }

    @Override
    public Table getColumnNames(String tableName) throws SQLException, NullPointerException {

        String query = "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = " + "'" + tableName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query))
        {
            ArrayList<ColumnDate> columnDates = new ArrayList<>();
            columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<>()));

            while (resultSet.next()){
                String data = resultSet.getString("COLUMN_NAME");
                columnDates.get(0).getValue().add(data);
            }

            return new Table(tableName, columnDates);
        }
    }

    @Override
    public Table getAllTypeColumns(String tableName) throws SQLException, NullPointerException{

        ArrayList<ColumnDate> columnDates = columnDates();

        String query = "SELECT COLUMN_NAME , DATA_TYPE, DATA_LENGTH, NULLABLE FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = "
                + "'" + tableName+ "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query))
        {
            resultSetGetHelper(columnDates, resultSet);
            return new Table(tableName, columnDates) ;
        }
    }

    @Override
    public Table getTypeColumn(String tableName, String columnName) throws SQLException, NullPointerException {

        ArrayList<ColumnDate> columnDates = columnDates();

        String query = "SELECT COLUMN_NAME , data_type, DATA_LENGTH, NULLABLE FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "' AND COLUMN_NAME = " + "'" + columnName + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query))
        {
            resultSetGetHelper(columnDates, resultSet);
            return new Table(tableName, columnDates);
        }
    }

    private ArrayList<ColumnDate> columnDates() {
        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<>()));
        columnDates.add(new ColumnDate("DATA_TYPE", new ArrayList<>()));
        columnDates.add(new ColumnDate("NULLABLE", new ArrayList<>()));
        return columnDates;
    }

    @Override
    public void createWithoutPK(String tableName, ArrayList<String> settings) throws SQLException, NullPointerException{

        String ulrSettings = "";
        for (int i = 0; i < settings.size() ; i++) {
            if(i == settings.size() - 1) {
                ulrSettings += settings.get(i);
            } else {
                ulrSettings += settings.get(i) + ", ";
            }
        }

        String query = "CREATE TABLE " + tableName + " (" + ulrSettings + " )";
        statExecUpdate(query);
    }

    @Override
    public void createCreatePK(String tableName, String columnNamePK) throws SQLException, NullPointerException {

        String query = "ALTER TABLE " + tableName + " ADD (CONSTRAINT " +  tableName + "_PK PRIMARY KEY (" + columnNamePK + "))";
        statExecUpdate(query);
    }

    @Override
    public void createSequencePK(String tableName, Long startWith) throws SQLException, NullPointerException{

        String query = "CREATE SEQUENCE " + tableName + "_seq START WITH " + startWith;
        statExecUpdate(query);
    }

    @Override
    public void insert(String tableName, ArrayList<String[]> nameDate, boolean idKey) throws SQLException, NullPointerException{

        String columnNames = getName(nameDate);
        String values = getValue(tableName, nameDate, idKey);

        String query = "INSERT INTO " + tableName + "( " + columnNames + ") VALUES ( " + values + " )";

        statExecUpdate(query);
    }

    private String getValue(String tableName, ArrayList<String[]> nameDate, boolean idKey) {
        String values = "";
        if(idKey) {
            for (int index = 0; index < nameDate.size(); index++) {
                if (index == 0) {
                    values += tableName + "_SEQ.nextval,";
                } else {
                    values += nameDate.get(index)[1];

                    if (index != nameDate.size() - 1) {
                        values += ", ";
                    }

                }
            }
        } else {
            int startFrom = 0;
            if (idKey) {
                startFrom = 1;
            }

            for (int j = startFrom; j < nameDate.size(); j++) {
                values += nameDate.get(j)[1];

                if (j != nameDate.size() - 1) {
                    values += ", ";
                }
            }
        }
        return values;
    }

    private String getName(ArrayList<String[]> nameDate) {
        String columnNames = "";
        for (int index = 0; index < nameDate.size(); index++){
            columnNames += nameDate.get(index)[0];
            if(index != nameDate.size() - 1){
                columnNames += ", ";
            }
        }
        return columnNames;
    }

    @Override
    public Table read(String tableName) throws SQLException, NullPointerException {

        Table contains = getTableNames();
        ArrayList<String> temp = new ArrayList<>();

        for (int index = 0; index < contains.getTableDate().get(0).getValue().size(); index++) {
            temp.add(contains.getTableDate().get(0).getValue().get(index));
        }

        if(temp.contains(tableName)){
            ArrayList<ColumnDate> columnDates = getColumnDates(tableName);
            String query = "SELECT * FROM " + tableName;
            return getTableHelper(tableName, columnDates, query);
        } else {
            throw new SQLException("ORA-00942: table or view does not exist");
        }
    }

    @Override
    public Table readSet(String tableName, ArrayList<String[]> settings) throws SQLException, NullPointerException {

        ArrayList<ColumnDate> columnDates = getColumnDates(tableName);

        String postQuery = generateQueryAndString(settings);

        String query = "SELECT * FROM " + tableName +  " WHERE " + postQuery;

        return getTableHelper(tableName, columnDates, query);

    }

    @Override
    public void update(String tableName, ArrayList<String[]> forUpdate, ArrayList<String[]> howUpdate)throws SQLException, NullPointerException {

        String perQuery = generateQueryComaString(forUpdate);
        String postQuery = generateQueryAndString(howUpdate);

        String query  = "UPDATE " + tableName +  " SET " + perQuery + " WHERE " + postQuery;

        statExecUpdate(query);
    }

    @Override
    public void drop(String tableName) throws SQLException, NullPointerException{
        statExecUpdate("DROP TABLE " + tableName);
    }

    @Override
    public void delete(String tableName, ArrayList<String[]> settings)  throws SQLException, NullPointerException{

        String postQuery = generateQueryAndString(settings);
        String query = "DELETE FROM " +  tableName + " WHERE " + postQuery;
        statExecUpdate(query);
    }

    @Override
    public void clear(String tableName) throws SQLException, NullPointerException {
        statExecUpdate("DELETE " + tableName);
    }

    @Override
    public void cudQuery(String query) throws SQLException, NullPointerException{
        statExecUpdate(query);
    }

    @Override
    public Table readQuery(String query) throws SQLException, NullPointerException {


        try (Statement statement =  connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query))
        {
            ArrayList<ColumnDate> columnDates = new ArrayList<>();
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int lengthOfCol = rsMetaData.getColumnCount();

            for (int i = 0; i < lengthOfCol; i++) {
                String columnName = rsMetaData.getColumnName(i + 1);
                columnDates.add(new ColumnDate(columnName, new ArrayList<>()));
            }

            sortHelper(columnDates, resultSet);

            return new Table( "Your Query", columnDates);
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void disconnect() {
        connection = null;
    }

    private void statExecUpdate(String query) throws SQLException, NullPointerException{
        try (Statement statement =  connection.createStatement())
        {
            statement.executeUpdate(query);
        }
    }

    private String generateQueryComaString(ArrayList<String[]> settings) {

        String query = "";

        for (int i = 0; i < settings.size() ; i++) {
            query += settings.get(i)[0] + " = " + settings.get(i)[1];

            if (i < settings.size() - 1){
                query += ", ";
            }
        }
        return query;
    }

    private String generateQueryAndString(ArrayList<String[]> settings) {

        String query = "";

        for (int i = 0; i < settings.size() ; i++) {
            query += settings.get(i)[0] + " = " + settings.get(i)[1];

            if (i < settings.size() - 1){
                query += " AND ";
            }
        }
        return query;
    }

    private Table getTableHelper(String tableName, ArrayList<ColumnDate> columnDates, String query) throws SQLException, NullPointerException{

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {
            sortHelper(columnDates, resultSet);
            return new Table(tableName, columnDates);
        }
    }

    private void sortHelper(ArrayList<ColumnDate> columnDates, ResultSet resultSet) throws SQLException, NullPointerException {

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

            String concat = stringType + "(" + stringLength + ")";
            columnDates.get(1).getValue().add(concat);

            String stringNullable = resultSet.getString("NULLABLE");
            columnDates.get(2).getValue().add(stringNullable);

        }
    }

    private ArrayList<ColumnDate> getColumnDates(String tableName) throws SQLException, NullPointerException {

        ArrayList<String> columnNames = getColumnNames(tableName).getTableDate().get(0).getValue();
        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            ColumnDate temp = new ColumnDate(columnNames.get(i), new ArrayList<>());
            columnDates.add(temp);
        }
        return columnDates;
    }
}

