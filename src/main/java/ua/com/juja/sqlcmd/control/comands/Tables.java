package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {

    }
}
