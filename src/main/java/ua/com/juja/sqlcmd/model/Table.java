package ua.com.juja.sqlcmd.model;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Table {


    private String tableName;
    private ColumnDate [] tableDate ;


    public  Table (String tableName, ColumnDate [] tableDate){
        this.tableName = tableName;
        this.tableDate = tableDate;
    }

    public String getTableName() {
        return tableName;
    }

    public ColumnDate[] getTableDate() {
        return tableDate;
    }

}
