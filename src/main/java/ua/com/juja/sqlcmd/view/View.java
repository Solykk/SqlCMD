package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.Table;

public interface View {

    void write(String message);
    String read();
    void printHistory();
    void writeAndHistory(String toWrite, String toHistory);
    void addHistory(String toHistory);

}
