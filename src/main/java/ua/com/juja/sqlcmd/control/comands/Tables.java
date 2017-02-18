package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Tables implements Command {

    private DatabaseManager manager;
    private View view;
    private ViewService viewService;

    public Tables(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {

        try {
            view.printTable(manager.getTableNames());
            viewService.tablesComTry();
        } catch (SQLException | NullPointerException e) {
            viewService.tablesComCatch(e.getMessage());
        }
    }
}
