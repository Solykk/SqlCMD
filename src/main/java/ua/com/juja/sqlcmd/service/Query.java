package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.ColumnData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Query {

    public String insertQuery(String tableName, List<String[]> nameDate, boolean idKey){
        return  "INSERT INTO " + tableName + "( " + getName(nameDate) + ") VALUES ( " + getValue(tableName, nameDate, idKey) + " )";
    }

    private String getValue(String tableName, List<String[]> nameDate, boolean idKey) {
        StringBuilder values = new StringBuilder();
        if(idKey) {
            for (int index = 0; index < nameDate.size(); index++) {
                if (index == 0) {
                    values.append(tableName).append("_SEQ.nextval, ");
                } else {
                    values.append(nameDate.get(index)[1]);

                    if (index != nameDate.size() - 1) {
                        values.append(", ");
                    }

                }
            }
        } else {

            int startFrom = 0;
            for (int j = startFrom; j < nameDate.size(); j++) {
                values.append(nameDate.get(j)[1]);

                if (j != nameDate.size() - 1) {
                    values.append(", ");
                }
            }
        }
        return values.toString();
    }

    private String getName(List<String[]> nameDate) {
        StringBuilder columnNames = new StringBuilder();
        for (int index = 0; index < nameDate.size(); index++){

            columnNames.append(nameDate.get(index)[0]);
            if(index != nameDate.size() - 1){
                columnNames.append(", ");
            }
        }
        return columnNames.toString();
    }

    public String createSPKQuery(String tableName, Long startWith){
        return "CREATE SEQUENCE " + tableName + "_seq START WITH " + startWith;
    }

    public String createPKQuery(String tableName, String columnNamePK){
        return "ALTER TABLE " + tableName + " ADD (CONSTRAINT " +  tableName + "_PK PRIMARY KEY (" + columnNamePK + "))";
    }

    public String createWPKQuery(String tableName, List<String> settings){
        StringBuilder postQuery = new StringBuilder();
        for (int i = 0; i < settings.size() ; i++) {
            if(i == settings.size() - 1) {
                postQuery.append(settings.get(i));
            } else {
                postQuery.append(settings.get(i)).append(", ");
            }
        }
        return  "CREATE TABLE " + tableName + " (" + postQuery + " )";
    }

    public String updateQuery(String tableName, List<String[]> howUpdate, List<String[]> forUpdate){
        return  "UPDATE " + tableName +  " SET " + generateQueryComaString(howUpdate) + " WHERE " + generateQueryAndString(forUpdate);
    }

    private String generateQueryComaString(List<String[]> settings) {

        StringBuilder query = new StringBuilder();

        for (int i = 0; i < settings.size() ; i++) {
            query.append(settings.get(i)[0]).append(" = ").append(settings.get(i)[1]);

            if (i < settings.size() - 1){
                query.append(", ");
            }
        }
        return query.toString();
    }

    private String generateQueryAndString(List<String[]> settings) {

        StringBuilder query = new StringBuilder();

        for (int i = 0; i < settings.size() ; i++) {
            query.append(settings.get(i)[0]).append(" = ").append(settings.get(i)[1]);

            if (i < settings.size() - 1){
                query.append(" AND ");
            }
        }
        return query.toString();
    }

    public String deleteQuery(String tableName, List<String[]> settings){
        return "DELETE FROM " +  tableName + " WHERE " + generateQueryAndString(settings);
    }

    public String readSetQuery(String tableName, List<String[]> settings){
        return  "SELECT * FROM " + tableName +  " WHERE " + generateQueryAndString(settings);
    }

    public String getTypCloQuery(String tableName, String columnName){
        return "SELECT COLUMN_NAME , data_type, DATA_LENGTH, NULLABLE FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "' AND COLUMN_NAME = " + "'" + columnName + "'";
    }

    public List<ColumnData> columnData() {
        return Stream.builder()
                .add(new ColumnData("COLUMN_NAME", new ArrayList<>()))
                .add(new ColumnData("DATA_TYPE", new ArrayList<>()))
                .add(new ColumnData("NULLABLE", new ArrayList<>()))
                .build()
                .map(o -> (ColumnData)o)
                .collect(Collectors.toList());
    }

    public String getAllTypCloQuery(String tableName){
        return "SELECT COLUMN_NAME , DATA_TYPE, DATA_LENGTH, NULLABLE FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = "
                + "'" + tableName+ "'";
    }

    public String tableNQuery(){
        return "SELECT table_name FROM information_schema.tables";
    }

    public List<ColumnData> tableNameRes() {
        return Stream.builder()
                .add(new ColumnData("TABLE_NAME", new ArrayList<>()))
                .build()
                .map(o -> (ColumnData)o)
                .collect(Collectors.toList());
    }

    public String getColNQuery(String tableName){
        return "SELECT column_name FROM information_schema.columns WHERE table_name = " + "'" + tableName + "'";
    }

    public List<ColumnData> columnNameRes() {
        return Stream.builder()
                .add(new ColumnData("COLUMN_NAME", new ArrayList<>()))
                .build()
                .map(o -> (ColumnData)o)
                .collect(Collectors.toList());
    }

    public String selectAll(String tableName){
        return "SELECT * FROM " + tableName;
    }
}