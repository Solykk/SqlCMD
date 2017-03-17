package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.service.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class  JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url;
    private final Query query = new Query();

    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        url = "jdbc:oracle:thin:/@localhost:1521:XE";
        this.connection = null;
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

        ArrayList<ColumnData> columnData = query.tableNameRes();

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

        ArrayList<ColumnData> columnDatas = query.columnNameRes();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getColNQuery(tableName)))
        {
            while (resultSet.next()){
                String data = resultSet.getString("COLUMN_NAME");
                columnDatas.get(0).getValue().add(data);
            }

            return new Table(tableName, columnDatas);
        }
    }

    @Override
    public Table getAllTypeColumns(String tableName) throws SQLException, NullPointerException{

        ArrayList<ColumnData> columnDatas = query.columnData();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getAllTypCloQuery(tableName)))
        {
            resultSetGetHelper(columnDatas, resultSet);
            return new Table(tableName, columnDatas) ;
        }
    }

    @Override
    public Table getTypeColumn(String tableName, String columnName) throws SQLException, NullPointerException {

        ArrayList<ColumnData> columnDatas = query.columnData();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getTypCloQuery(tableName, columnName)))
        {
            resultSetGetHelper(columnDatas, resultSet);
            return new Table(tableName, columnDatas);
        }
    }

    @Override
    public void createWithoutPK(String tableName, ArrayList<String> settings) throws SQLException, NullPointerException{
        statExecUpdate(query.createWPKQuery(tableName, settings));
    }

    @Override
    public void createCreatePK(String tableName, String columnNamePK) throws SQLException, NullPointerException, NumberFormatException {
        statExecUpdate(query.createPKQuery(tableName, columnNamePK));
    }

    @Override
    public void createSequencePK(String tableName, Long startWith) throws SQLException, NullPointerException, NumberFormatException{
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

    private void sortHelper(ArrayList<ColumnData> columnDatas, ResultSet resultSet) throws SQLException, NullPointerException {

        while (resultSet.next()){
            for(int index = 0; index < columnDatas.size(); index++){
                String columnName = columnDatas.get(index).columnName();
                String temp = resultSet.getString(columnName);

                columnDatas.get(index).getValue().add(temp);
            }
        }
    }

    private void resultSetGetHelper(ArrayList<ColumnData> columnDatas, ResultSet resultSet) throws SQLException, NullPointerException {

        while (resultSet.next()){

            String stringName = resultSet.getString("COLUMN_NAME");
            columnDatas.get(0).getValue().add(stringName);

            String stringType = resultSet.getString("DATA_TYPE");
            String stringLength = resultSet.getString("DATA_LENGTH");

            String concat = stringType + "(" + stringLength + ")";
            columnDatas.get(1).getValue().add(concat);

            String stringNullable = resultSet.getString("NULLABLE");
            columnDatas.get(2).getValue().add(stringNullable);

        }
    }

    private Table getTableHelper(String tableName, ArrayList<ColumnData> columnDatas, String query) throws SQLException, NullPointerException{

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {
            sortHelper(columnDatas, resultSet);
            return new Table(tableName, columnDatas);
        }
    }

    private ArrayList<ColumnData> getColumnData(String tableName) throws SQLException, NullPointerException {

        ArrayList<String> columnNames = getColumnNames(tableName).getTableData().get(0).getValue();
        ArrayList<ColumnData> columnDatas = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); i++) {
            ColumnData temp = new ColumnData(columnNames.get(i), new ArrayList<>());
            columnDatas.add(temp);
        }
        return columnDatas;
    }

    private ArrayList<String> getContent() throws SQLException, NullPointerException {
        ArrayList<String> temp = new ArrayList<>();

        Table contains = getTableNames();
        for (int index = 0; index < contains.getTableData().get(0).getValue().size(); index++) {
            temp.add(contains.getTableData().get(0).getValue().get(index));
        }
        return temp;
    }

    private ArrayList<ColumnData> getDataRead(ResultSet resultSet, ResultSetMetaData rsMetaData, int lengthOfCol) throws SQLException {
        ArrayList<ColumnData> columnData = new ArrayList<>();

        for (int i = 0; i < lengthOfCol; i++) {
            String columnName = rsMetaData.getColumnName(i + 1);
            columnData.add(new ColumnData(columnName, new ArrayList<>()));
        }

        sortHelper(columnData, resultSet);
        return columnData;
    }
}

