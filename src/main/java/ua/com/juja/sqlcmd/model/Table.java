package ua.com.juja.sqlcmd.model;

import java.util.ArrayList;

public class Table {

    private String tableName;
    private ArrayList<ColumnDate> tableDate ;

    public  Table (String tableName, ArrayList<ColumnDate> tableDate){
        this.tableName = tableName;
        this.tableDate = tableDate;
    }

    public String getTableName() {
        return tableName;
    }

    public ArrayList<ColumnDate> getTableDate() {
        return tableDate;
    }

}
