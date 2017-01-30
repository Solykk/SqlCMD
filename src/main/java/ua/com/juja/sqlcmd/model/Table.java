package ua.com.juja.sqlcmd.model;

import java.util.List;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Table {


    private String tableName;
    private List<ColumnDate>  tableDate ;


    public  Table (String tableName, List<ColumnDate> tableDate){
        this.tableName = tableName;
        this.tableDate = tableDate;
    }

    public String getTableName() {
        return tableName;
    }

    public List<ColumnDate> getTableDate() {
        return tableDate;
    }

}
