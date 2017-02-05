package ua.com.juja.sqlcmd.control.comands;

/**
 * Created by Solyk on 04.02.2017.
 */
public interface Comand {

    boolean isProcessed(String command);

    void  process(String command);
}
