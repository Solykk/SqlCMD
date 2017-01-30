package ua.com.juja.sqlcmd.model;

import java.util.List;

/**
 * Created by Solyk on 26.01.2017.
 */
public class ColumnDate {

    private String columnName;
    private List<String> value;


    public ColumnDate (String columnName, List<String> value){
        this.columnName = columnName;
        this.value = value;
    }

    public String columnName(){
        return columnName;
    }

    public List<String> getValue(){
        return value;
    }
}
