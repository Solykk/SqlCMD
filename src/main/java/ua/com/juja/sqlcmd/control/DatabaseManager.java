package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.Table;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface DatabaseManager {

    boolean  connect(String userName, String dbPassword) throws SQLException;

    Table getAllTableNames() throws SQLException, NullPointerException;
    Table getAllColumnNamesFromTable(String tableName) throws SQLException, NullPointerException;
    Table getDataTypeAllColumnsFromTable(String tableName)throws SQLException, NullPointerException;
    Table getDataTypeColumnFromTable(String tableName, String columnName)  throws SQLException, NullPointerException;

    boolean createTableCreatePK(String tableName, String columnNamePK)throws SQLException, NullPointerException;
    boolean createTableWithoutPK(String tableName, ArrayList<String> settings)throws SQLException, NullPointerException;
    boolean createTableSequenceForPK(String tableName, Long startWith)throws SQLException, NullPointerException;

    Table readTable(String tableName) throws SQLException, NullPointerException;
    Table read(String tableName, ArrayList<String[]> settings) throws SQLException, NullPointerException;

    boolean insert(String tableName, ArrayList<String[]> columnNameVSdata, boolean isKey) throws SQLException, NullPointerException;
    boolean update(String tableName, ArrayList<String[]> settings, ArrayList<String[]> settingsHowUpdate)throws SQLException, NullPointerException;
    boolean drop(String tableName) throws SQLException, NullPointerException;
    boolean delete(String tableName, ArrayList<String[]> settings)  throws SQLException, NullPointerException;
    boolean clear(String tableName)throws SQLException, NullPointerException;

    boolean cudQuery(String query)throws SQLException, NullPointerException;
    Table readQuery(String query)throws SQLException, NullPointerException;

    boolean isConnected();

}
