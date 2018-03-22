package ua.com.juja.sqlcmd.control.commands;

public interface Command  {

    boolean isProcessed(String command);
    void  process(String command);

}