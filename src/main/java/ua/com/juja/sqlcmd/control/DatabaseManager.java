package ua.com.juja.sqlcmd.control;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface DatabaseManager {

    boolean  connect(String userName, String dbPassword);

    ArrayList<String> getAllTableNames();
    ArrayList<String> getAllColumnNamesFromTable(String tableName);
    ArrayList<String> getDataTypeColumnFromTable(String tableName, String columnName);
    ArrayList<String[]> getDataTypeAllColumnsFromTable(String tableName);

    boolean createTable(String tableName, ArrayList<String[]> settings, boolean keySeq);

    //    Long create(Client client);
//    Client read(Long id);
//    boolean update(Client client);
//    boolean delete(Client client);



}
