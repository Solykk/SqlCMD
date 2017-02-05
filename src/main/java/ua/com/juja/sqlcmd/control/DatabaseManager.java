package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.model.Table;

import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface DatabaseManager {

    boolean  connect(String userName, String dbPassword);

    Table getAllTableNames();
    Table getAllColumnNamesFromTable(String tableName);
    Table getDataTypeAllColumnsFromTable(String tableName);
    Table getDataTypeColumnFromTable(String tableName, String columnName);

    boolean createTableWithPK(String tableName, ArrayList<String> settings, String columnNamePK, Long startWith);
    boolean createTableWithoutPK(String tableName, ArrayList<String> settings);

    Table readTable(String tableName);
    Table read(String tableName, ArrayList<String[]> settings);

    boolean insert(String tableName, ArrayList<String[]> columnNameVSdata, boolean isKey);
    boolean update(String tableName, ArrayList<String[]> settings,ArrayList<String[]> settingsHowUpdate);
    boolean drop(String tableName);
    boolean delete(String tableName, ArrayList<String[]> settings);
    boolean clear(String tableName);

    boolean cudQuery(String query);
    Table readQuery(String query);

}
