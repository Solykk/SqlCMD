package ua.com.juja.sqlcmd.model;

import java.util.List;

public class ColumnData {

    private String columnName;
    private List<String> value;

    public ColumnData(String columnName, List<String> value){
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