package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Columns implements Command {

    private DatabaseManager manager;
    private View view;

    public Columns(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("columns|");
    }

    @Override
    public void process(String command) {

    }
}
