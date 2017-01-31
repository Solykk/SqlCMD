package ua.com.juja.sqlcmd.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solyk on 26.01.2017.
 */
public class DataSet {

    private ArrayList<Table> tables;

    public DataSet(ArrayList<Table> tables){
        this.tables = tables;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }


}
