package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.Table;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface View {

    void write(String message);
    String read();
    String printTable(Table table);
    void printHistory();
    void writeAndHistory(String toWrite, String toHistory);
    void addHistory(String toHistory);

}
