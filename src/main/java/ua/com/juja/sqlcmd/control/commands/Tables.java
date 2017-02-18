package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Tables implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public Tables(DatabaseManager manager, View view) {
        this.manager = manager;
        this.viewService = new ViewService(view);
        this.tablePrinter = new TablePrinter(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {

        try {
            tablePrinter.printTable(manager.getTableNames());
            viewService.tablesComTry();
        } catch (SQLException | NullPointerException e) {
            viewService.tablesComCatch(e.getMessage());
        }
    }
}
