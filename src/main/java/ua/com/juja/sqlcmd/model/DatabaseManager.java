package ua.com.juja.sqlcmd.model;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseManager {

    void  connect(String userName, String dbPassword) throws SQLException;

    Table getTableNames() throws SQLException;
    Table getColumnNames(String tableName) throws SQLException;
    Table getAllTypeColumns(String tableName) throws SQLException;
    Table getTypeColumn(String tableName, String columnName) throws SQLException;

    void createCreatePK(String tableName, String columnNamePK) throws SQLException;
    void createWithoutPK(String tableName, List<String> settings) throws SQLException;
    void createSequencePK(String tableName, Long startWith) throws SQLException;

    Table read(String tableName) throws SQLException;
    Table readSet(String tableName, List<String[]> settings) throws SQLException;

    void insert(String tableName, List<String[]> nameDate, boolean isKey) throws SQLException;
    void update(String tableName, List<String[]> forUpdate, List<String[]> howUpdate) throws SQLException;
    void drop(String tableName) throws SQLException;
    void delete(String tableName, List<String[]> settings) throws SQLException;
    void clear(String tableName) throws SQLException;

    void cudQuery(String query) throws SQLException;
    Table readQuery(String query) throws SQLException;

    boolean isConnected();
    void disconnect();

}