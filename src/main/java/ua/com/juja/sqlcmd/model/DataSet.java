package ua.com.juja.sqlcmd.model;

import java.util.List;

/**
 * Created by Solyk on 26.01.2017.
 */
public class DataSet {

    private List<Table> tables;

    public DataSet(List<Table> tables){
        this.tables = tables;
    }

    public List<Table> getTables() {
        return tables;
    }


}
