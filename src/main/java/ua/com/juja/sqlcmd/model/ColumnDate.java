package ua.com.juja.sqlcmd.model;

import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public class ColumnDate {

    private String columnName;
    private ArrayList<String> value;

    public ColumnDate (String columnName, ArrayList<String> value){
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
