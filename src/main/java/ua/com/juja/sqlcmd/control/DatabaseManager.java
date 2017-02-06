package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.Table;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface DatabaseManager {

    void  connect(String userName, String dbPassword) throws SQLException;

    Table getAllTableNames() throws SQLException, NullPointerException;
    Table getAllColumnNamesFromTable(String tableName) throws SQLException, NullPointerException;
    Table getDataTypeAllColumnsFromTable(String tableName)throws SQLException, NullPointerException;
    Table getDataTypeColumnFromTable(String tableName, String columnName)  throws SQLException, NullPointerException;

    void createTableCreatePK(String tableName, String columnNamePK)throws SQLException, NullPointerException;
    void createTableWithoutPK(String tableName, ArrayList<String> settings)throws SQLException, NullPointerException;
    void createTableSequenceForPK(String tableName, Long startWith)throws SQLException, NullPointerException;

    Table readTable(String tableName) throws SQLException, NullPointerException;
    Table read(String tableName, ArrayList<String[]> settings) throws SQLException, NullPointerException;

    void insert(String tableName, ArrayList<String[]> columnNameVSdata, boolean isKey) throws SQLException, NullPointerException;
    void update(String tableName, ArrayList<String[]> settings, ArrayList<String[]> settingsHowUpdate)throws SQLException, NullPointerException;
    void drop(String tableName) throws SQLException, NullPointerException;
    void delete(String tableName, ArrayList<String[]> settings)  throws SQLException, NullPointerException;
    void clear(String tableName)throws SQLException, NullPointerException;

    void cudQuery(String query)throws SQLException, NullPointerException;
    Table readQuery(String query)throws SQLException, NullPointerException;

    boolean isConnected();

}
