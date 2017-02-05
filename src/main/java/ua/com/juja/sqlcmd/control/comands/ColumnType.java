package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 05.02.2017.
 */
public class ColumnType implements Command {

    private DatabaseManager manager;
    private View view;

    public ColumnType(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("columntype|");
    }

    @Override
    public void process(String command) {

    }
}
