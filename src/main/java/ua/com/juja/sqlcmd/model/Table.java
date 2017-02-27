package ua.com.juja.sqlcmd.model;

import java.util.ArrayList;

public class Table {

    private String tableName;
    private ArrayList<ColumnData> tableDate ;

    public  Table (String tableName, ArrayList<ColumnData> tableDate){
        this.tableName = tableName;
        this.tableDate = tableDate;
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<ColumnData> getTableDate() {
        return tableDate;
    }

}
