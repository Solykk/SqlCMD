package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.model.Table;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseManager {

    void  connect(String userName, String dbPassword) throws SQLException;

    Table getTableNames() throws SQLException, NullPointerException;
    Table getColumnNames(String tableName) throws SQLException, NullPointerException;
    Table getAllTypeColumns(String tableName)throws SQLException, NullPointerException;
    Table getTypeColumn(String tableName, String columnName)  throws SQLException, NullPointerException;

    void createCreatePK(String tableName, String columnNamePK)throws SQLException, NullPointerException, NumberFormatException;
    void createWithoutPK(String tableName, ArrayList<String> settings)throws SQLException, NullPointerException;
    void createSequencePK(String tableName, Long startWith)throws SQLException, NullPointerException, NumberFormatException;

    Table read(String tableName) throws SQLException, NullPointerException;
    Table readSet(String tableName, ArrayList<String[]> settings) throws SQLException, NullPointerException;

    void insert(String tableName, ArrayList<String[]> nameDate, boolean isKey) throws SQLException, NullPointerException;
    void update(String tableName, ArrayList<String[]> forUpdate, ArrayList<String[]> howUpdate)throws SQLException, NullPointerException;
    void drop(String tableName) throws SQLException, NullPointerException;
    void delete(String tableName, ArrayList<String[]> settings)  throws SQLException, NullPointerException;
    void clear(String tableName)throws SQLException, NullPointerException;

    void cudQuery(String query)throws SQLException, NullPointerException;
    Table readQuery(String query)throws SQLException, NullPointerException;

    boolean isConnected();
    void disconnect();

}
