package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.service.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class  JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url;
    private final Query query = new Query();

    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        url = "jdbc:postgresql://localhost:5432/payment_gateway_test";
        this.connection = null;
    }

    @Override
    public void connect(String userName, String dbPassword) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
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
    public Table getTableNames() throws SQLException {

        List<ColumnData> columnData = query.tableNameRes();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.tableNQuery()))
        {
            while (resultSet.next()){
                columnData.get(0).getValue().add(resultSet.getString("TABLE_NAME"));
            }

            return new Table("ALL_TABLES", columnData);
        }
    }

    @Override
    public Table getColumnNames(String tableName) throws SQLException {

        List<ColumnData> columnData = query.columnNameRes();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getColNQuery(tableName)))
        {
            while (resultSet.next()){
                columnData.get(0).getValue().add(resultSet.getString("COLUMN_NAME"));
            }

            return new Table(tableName, columnData);
        }
    }

    @Override
    public Table getAllTypeColumns(String tableName) throws SQLException {

        List<ColumnData> columnData = query.columnData();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getAllTypCloQuery(tableName)))
        {
            resultSetGetHelper(columnData, resultSet);
            return new Table(tableName, columnData) ;
        }
    }

    @Override
    public Table getTypeColumn(String tableName, String columnName) throws SQLException {

        List<ColumnData> columnData = query.columnData();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query.getTypCloQuery(tableName, columnName)))
        {
            resultSetGetHelper(columnData, resultSet);
            return new Table(tableName, columnData);
        }
    }

    @Override
    public void createWithoutPK(String tableName, List<String> settings) throws SQLException {
        statExecUpdate(query.createWPKQuery(tableName, settings));
    }

    @Override
    public void createCreatePK(String tableName, String columnNamePK) throws SQLException {
        statExecUpdate(query.createPKQuery(tableName, columnNamePK));
    }

    @Override
    public void createSequencePK(String tableName, Long startWith) throws SQLException {
        statExecUpdate(query.createSPKQuery(tableName, startWith));
    }

    @Override
    public void insert(String tableName, List<String[]> nameDate, boolean idKey) throws SQLException {
        statExecUpdate(query.insertQuery(tableName, nameDate, idKey));
    }

    @Override
    public Table read(String tableName) throws SQLException {
        return getContent().contains(tableName)
                ? getTableHelper(tableName, getColumnData(tableName), query.selectAll(tableName))
                : throwSQLException("ORA-00942: table or view does not exist");
    }

    private Table throwSQLException(String message) throws SQLException {
        throw new SQLException(message);
    }

    @Override
    public Table readSet(String tableName, List<String[]> settings) throws SQLException {
        return getTableHelper(tableName, getColumnData(tableName), query.readSetQuery(tableName, settings));
    }

    @Override
    public void update(String tableName, List<String[]> howUpdate, List<String[]> forUpdate)throws SQLException {
        statExecUpdate(query.updateQuery(tableName, howUpdate, forUpdate));
    }

    @Override
    public void drop(String tableName) throws SQLException {
        statExecUpdate("DROP TABLE " + tableName);
    }

    @Override
    public void delete(String tableName, List<String[]> settings)  throws SQLException {
        statExecUpdate(query.deleteQuery(tableName, settings));
    }

    @Override
    public void clear(String tableName) throws SQLException {
        statExecUpdate("DELETE " + tableName);
    }

    @Override
    public void cudQuery(String query) throws SQLException {
        statExecUpdate(query);
    }

    @Override
    public Table readQuery(String query) throws SQLException {
        try (Statement statement =  connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query))
        {
            return new Table( "Your Query", getDataRead(resultSet, resultSet.getMetaData(), resultSet.getMetaData().getColumnCount()));
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

    private void statExecUpdate(String query) throws SQLException {
        try (Statement statement =  connection.createStatement())
        {
            statement.executeUpdate(query);
        }
    }

    private void sortHelper(List<ColumnData> columnDatas, ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            for (ColumnData columnData : columnDatas) {
                columnData.getValue().add(resultSet.getString(columnData.columnName()));
            }
        }
    }

    private void resultSetGetHelper(List<ColumnData> columnData, ResultSet resultSet) throws SQLException {
        while (resultSet.next()){
            columnData.get(0).getValue().add(resultSet.getString("COLUMN_NAME"));
            columnData.get(1).getValue().add(resultSet.getString("DATA_TYPE") + "(" + resultSet.getString("DATA_LENGTH") + ")");
            columnData.get(2).getValue().add(resultSet.getString("NULLABLE"));
        }
    }

    private Table getTableHelper(String tableName, List<ColumnData> columnData, String query) throws SQLException {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query))
        {
            sortHelper(columnData, resultSet);
            return new Table(tableName, columnData);
        }
    }

    private List<ColumnData> getColumnData(String tableName) throws SQLException {
        List<String> columnNames = getColumnNames(tableName).getTableData().get(0).getValue();
        List<ColumnData> columnData = new ArrayList<>();
        for (String columnName : columnNames) {
            columnData.add(new ColumnData(columnName, new ArrayList<>()));
        }
        return columnData;
    }

    private List<String> getContent() throws SQLException {
        List<String> temp = new ArrayList<>();
        Table contains = getTableNames();
        for (int index = 0; index < contains.getTableData().get(0).getValue().size(); index++) {
            temp.add(contains.getTableData().get(0).getValue().get(index));
        }
        return temp;
    }

    private List<ColumnData> getDataRead(ResultSet resultSet, ResultSetMetaData rsMetaData, int lengthOfCol) throws SQLException {
        List<ColumnData> columnData = new ArrayList<>();

        for (int i = 0; i < lengthOfCol; i++) {
            columnData.add(new ColumnData(rsMetaData.getColumnName(i + 1), new ArrayList<>()));
        }

        sortHelper(columnData, resultSet);
        return columnData;
    }
}