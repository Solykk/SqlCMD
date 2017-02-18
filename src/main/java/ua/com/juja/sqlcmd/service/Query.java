package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.ColumnDate;

import java.util.ArrayList;

public class Query {

    public String insertQuery(String tableName, ArrayList<String[]> nameDate, boolean idKey){
        String columnNames = getName(nameDate);
        String values = getValue(tableName, nameDate, idKey);

        return  "INSERT INTO " + tableName + "( " + columnNames + ") VALUES ( " + values + " )";
    }

    private String getValue(String tableName, ArrayList<String[]> nameDate, boolean idKey) {
        String values = "";
        if(idKey) {
            for (int index = 0; index < nameDate.size(); index++) {
                if (index == 0) {
                    values += tableName + "_SEQ.nextval, ";
                } else {
                    values += nameDate.get(index)[1];

                    if (index != nameDate.size() - 1) {
                        values += ", ";
                    }

                }
            }
        } else {

            int startFrom = 0;
            for (int j = startFrom; j < nameDate.size(); j++) {
                values += nameDate.get(j)[1];

                if (j != nameDate.size() - 1) {
                    values += ", ";
                }
            }
        }
        return values;
    }

    private String getName(ArrayList<String[]> nameDate) {
        String columnNames = "";
        for (int index = 0; index < nameDate.size(); index++){

            columnNames += nameDate.get(index)[0];
            if(index != nameDate.size() - 1){
                columnNames += ", ";
            }
        }
        return columnNames;
    }

    public String createSPKQuery(String tableName, Long startWith){
        return "CREATE SEQUENCE " + tableName + "_seq START WITH " + startWith;
    }

    public String createPKQuery(String tableName, String columnNamePK){
        return "ALTER TABLE " + tableName + " ADD (CONSTRAINT " +  tableName + "_PK PRIMARY KEY (" + columnNamePK + "))";
    }

    public String createWPKQuery(String tableName, ArrayList<String> settings){
        String postQuery = "";
        for (int i = 0; i < settings.size() ; i++) {
            if(i == settings.size() - 1) {
                postQuery += settings.get(i);
            } else {
                postQuery += settings.get(i) + ", ";
            }
        }
        return  "CREATE TABLE " + tableName + " (" + postQuery + " )";
    }

    public String updateQuery(String tableName, ArrayList<String[]> howUpdate, ArrayList<String[]> forUpdate){
        String perQuery = generateQueryComaString(howUpdate);
        String postQuery = generateQueryAndString(forUpdate);

        return  "UPDATE " + tableName +  " SET " + perQuery + " WHERE " + postQuery;
    }

    private String generateQueryComaString(ArrayList<String[]> settings) {

        String query = "";

        for (int i = 0; i < settings.size() ; i++) {
            query += settings.get(i)[0] + " = " + settings.get(i)[1];

            if (i < settings.size() - 1){
                query += ", ";
            }
        }
        return query;
    }

    private String generateQueryAndString(ArrayList<String[]> settings) {

        String query = "";

        for (int i = 0; i < settings.size() ; i++) {
            query += settings.get(i)[0] + " = " + settings.get(i)[1];

            if (i < settings.size() - 1){
                query += " AND ";
            }
        }
        return query;
    }

    public String deleteQuery(String tableName, ArrayList<String[]> settings){
        String postQuery = generateQueryAndString(settings);
        return "DELETE FROM " +  tableName + " WHERE " + postQuery;
    }

    public String readSetQuery(String tableName, ArrayList<String[]> settings){
        String postQuery = generateQueryAndString(settings);
        return  "SELECT * FROM " + tableName +  " WHERE " + postQuery;
    }

    public String getTypCloQuery(String tableName, String columnName){
        return "SELECT COLUMN_NAME , data_type, DATA_LENGTH, NULLABLE FROM all_tab_columns WHERE TABLE_NAME = "
                + "'" + tableName+ "' AND COLUMN_NAME = " + "'" + columnName + "'";
    }

    public ArrayList<ColumnDate> columnDates() {
        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<>()));
        columnDates.add(new ColumnDate("DATA_TYPE", new ArrayList<>()));
        columnDates.add(new ColumnDate("NULLABLE", new ArrayList<>()));
        return columnDates;
    }

    public String getAllTypCloQuery(String tableName){
        return "SELECT COLUMN_NAME , DATA_TYPE, DATA_LENGTH, NULLABLE FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = "
                + "'" + tableName+ "'";
    }

    public String tableNQuery(){
        return "SELECT TABLE_NAME FROM user_tables";
    }

    public ArrayList<ColumnDate> tableNameRes() {
        ArrayList<ColumnDate> columnData = new ArrayList<>();
        columnData.add(new ColumnDate("TABLE_NAME", new ArrayList<>()));
        return columnData;
    }

    public String getColNQuery(String tableName){
        return "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = " + "'" + tableName + "'";
    }

    public ArrayList<ColumnDate> columnNameRes() {
        ArrayList<ColumnDate> columnDates = new ArrayList<>();
        columnDates.add(new ColumnDate("COLUMN_NAME", new ArrayList<>()));
        return columnDates;
    }

    public String selectAll(String tableName){
        return "SELECT * FROM " + tableName;
    }
}
