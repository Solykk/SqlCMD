package ua.com.juja.sqlcmd.model;

/**
 * Created by Solyk on 26.01.2017.
 */
public class ColumnDate {

    private String columnName;
    private String[] value;


    public ColumnDate (String columnName, String [] value){
        this.columnName = columnName;
        this.value = value;
    }

    public String columnName(){
        return columnName;
    }

    public String[] getValue(){
        return value;
    }
}
