package ua.com.juja.sqlcmd.model;

import java.util.ArrayList;

public class ColumnData {

    private String columnName;
    private ArrayList<String> value;

    public ColumnData(String columnName, ArrayList<String> value){
        this.columnName = columnName;
        this.value = value;
    }

    public String columnName(){
        return columnName;
    }

    public ArrayList<String> getValue(){
        return value;
    }
}
