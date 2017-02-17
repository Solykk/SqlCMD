package ua.com.juja.sqlcmd.control.comands;

public interface Command  {

    boolean isProcessed(String command);
    void  process(String command);

}
