package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.Table;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseManager {

    void  connect(String userName, String dbPassword) throws SQLException;

    Table getTableNames() throws SQLException, NullPointerException;
    Table getColumnNames(String tableName) throws SQLException, NullPointerException;
    Table getAllTypeColumns(String tableName)throws SQLException, NullPointerException;
    Table getTypeColumn(String tableName, String columnName)  throws SQLException, NullPointerException;

    void createCreatePK(String tableName, String columnNamePK)throws SQLException, NullPointerException;
    void createWithoutPK(String tableName, ArrayList<String> settings)throws SQLException, NullPointerException;
    void createSequencePK(String tableName, Long startWith)throws SQLException, NullPointerException;

    Table read(String tableName) throws SQLException, NullPointerException;
    Table readSet(String tableName, ArrayList<String[]> settings) throws SQLException, NullPointerException;

    void insert(String tableName, ArrayList<String[]> columnNameVSdata, boolean isKey) throws SQLException, NullPointerException;
    void update(String tableName, ArrayList<String[]> settings, ArrayList<String[]> settingsHowUpdate)throws SQLException, NullPointerException;
    void drop(String tableName) throws SQLException, NullPointerException;
    void delete(String tableName, ArrayList<String[]> settings)  throws SQLException, NullPointerException;
    void clear(String tableName)throws SQLException, NullPointerException;

    void cudQuery(String query)throws SQLException, NullPointerException;
    Table readQuery(String query)throws SQLException, NullPointerException;

    boolean isConnected();
    void disconnect();

}
