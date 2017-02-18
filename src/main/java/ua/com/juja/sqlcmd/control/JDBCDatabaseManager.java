package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.ColumnDate;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.service.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class  JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url;
    private Query query;

    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        this.connection = null;
        this.url = "jdbc:oracle:thin:/@localhost:1521:XE";
        this.query = new Query();
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

        ArrayList<ColumnDate> columnData = query.tableNameRes();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.tableNQuery()))
        {
            while (resultSet.next()){
                String data = resultSet.getString("TABLE_NAME");
                columnData.get(0).getValue().add(data);
            }

            return new Table("ALL_TABLES", columnData);
        }
    }

    @Override
    public Table getColumnNames(String tableName) throws SQLException, NullPointerException {

        ArrayList<ColumnDate> columnDates = query.columnNameRes();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getColNQuery(tableName)))
        {
            while (resultSet.next()){
                String data = resultSet.getString("COLUMN_NAME");
                columnDates.get(0).getValue().add(data);
            }

            return new Table(tableName, columnDates);
        }
    }

    @Override
    public Table getAllTypeColumns(String tableName) throws SQLException, NullPointerException{

        ArrayList<ColumnDate> columnDates = query.columnDates();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getAllTypCloQuery(tableName)))
        {
            resultSetGetHelper(columnDates, resultSet);
            return new Table(tableName, columnDates) ;
        }
    }

    @Override
    public Table getTypeColumn(String tableName, String columnName) throws SQLException, NullPointerException {

        ArrayList<ColumnDate> columnDates = query.columnDates();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getTypCloQuery(tableName, columnName)))
        {
            resultSetGetHelper(columnDates, resultSet);
            return new Table(tableName, columnDates);
        }
    }

    @Override
    public void createWithoutPK(String tableName, ArrayList<String> settings) throws SQLException, NullPointerException{
        statExecUpdate(query.createWPKQuery(tableName, settings));
    }

    @Override
    public void createCreatePK(String tableName, String columnNamePK) throws SQLException, NullPointerException {
        statExecUpdate(query.createPKQuery(tableName, columnNamePK));
    }

    @Override
    public void createSequencePK(String tableName, Long startWith) throws SQLException, NullPointerException{
        statExecUpdate(query.createSPKQuery(tableName, startWith));
    }

    @Override
    public void insert(String tableName, ArrayList<String[]> nameDate, boolean idKey) throws SQLException, NullPointerException{
        statExecUpdate(query.insertQuery(tableName, nameDate, idKey));
    }

    @Override
    public Table read(String tableName) throws SQLException, NullPointerException {
        if (getContent().contains(tableName)){
            return getTableHelper(tableName, getColumnData(tableName), query.selectAll(tableName));
        } else {
            throw new SQLException("ORA-00942: table or view does not exist");
        }
    }

    @Override
    public Table readSet(String tableName, ArrayList<String[]> settings) throws SQLException, NullPointerException {
        return getTableHelper(tableName, getColumnData(tableName), query.readSetQuery(tableName, settings));
    }

    @Override
    public void update(String tableName, ArrayList<String[]> howUpdate, ArrayList<String[]> forUpdate)throws SQLException, NullPointerException {
        statExecUpdate(query.updateQuery(tableName, howUpdate, forUpdate));
    }

    @Override
    public void drop(String tableName) throws SQLException, NullPointerException{
        statExecUpdate("DROP TABLE " + tableName);
    }

    @Override
    public void delete(String tableName, ArrayList<String[]> settings)  throws SQLException, NullPointerException{
        statExecUpdate(query.deleteQuery(tableName, settings));
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
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            int lengthOfCol = rsMetaData.getColumnCount();

            return new Table( "Your Query", getDataRead(resultSet, rsMetaData, lengthOfCol));
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

    private Table getTableHelper(String tableName, ArrayList<ColumnDate> columnDates, String query) throws SQLException, NullPointerException{

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {
            sortHelper(columnDates, resultSet);
            return new Table(tableName, columnDates);
        }
    }

    private ArrayList<ColumnDate> getColumnData(String tableName) throws SQLException, NullPointerException {

        ArrayList<String> columnNames = getColumnNames(tableName).getTableDate().get(0).getValue();
        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            ColumnDate temp = new ColumnDate(columnNames.get(i), new ArrayList<>());
            columnDates.add(temp);
        }
        return columnDates;
    }

    private ArrayList<String> getContent() throws SQLException, NullPointerException {
        ArrayList<String> temp = new ArrayList<>();

        Table contains = getTableNames();
        for (int index = 0; index < contains.getTableDate().get(0).getValue().size(); index++) {
            temp.add(contains.getTableDate().get(0).getValue().get(index));
        }
        return temp;
    }

    private ArrayList<ColumnDate> getDataRead(ResultSet resultSet, ResultSetMetaData rsMetaData, int lengthOfCol) throws SQLException {
        ArrayList<ColumnDate> columnData = new ArrayList<>();

        for (int i = 0; i < lengthOfCol; i++) {
            String columnName = rsMetaData.getColumnName(i + 1);
            columnData.add(new ColumnDate(columnName, new ArrayList<>()));
        }

        sortHelper(columnData, resultSet);
        return columnData;
    }
}

