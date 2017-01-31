package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.Table;

import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface DatabaseManager {

    boolean  connect(String userName, String dbPassword);

    ArrayList<String> getAllTableNames();
    ArrayList<String> getAllColumnNamesFromTable(String tableName);
    ArrayList<String> getDataTypeColumnFromTable(String tableName, String columnName);
    ArrayList<String[]> getDataTypeAllColumnsFromTable(String tableName);

    boolean createTableWithPK(String tableName, ArrayList<String[]> settings, String columnNamePK, Long startWith);
    boolean createTableWithoutPK(String tableName, ArrayList<String[]> settings);

    boolean createData(String tableName, ArrayList<String[]> columnNameVSdata, boolean isKey);
    Table readTable(String tableName);
    Table read(String tableName, ArrayList<String[]> settings);
    boolean update(String tableName, ArrayList<String[]> settings);
    boolean delete(String tableName, ArrayList<String[]> settings);



}
