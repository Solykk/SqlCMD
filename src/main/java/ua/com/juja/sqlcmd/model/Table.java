package ua.com.juja.sqlcmd.model;

import java.util.ArrayList;

public class Table {

    private String tableName;
    private ArrayList<ColumnData> tableData;

    public  Table (String tableName, ArrayList<ColumnData> tableData){
        this.tableName = tableName;
        this.tableData = tableData;
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<ColumnData> getTableData() {
        return tableData;
    }

}
