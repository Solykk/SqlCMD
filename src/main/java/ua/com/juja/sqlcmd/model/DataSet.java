package ua.com.juja.sqlcmd.model;

/**
 * Created by Solyk on 26.01.2017.
 */
public class DataSet {

    private Table [] tables;

    public DataSet(Table [] tables){
        this.tables = tables;
    }

    public Table[] getTables() {
        return tables;
    }


}
