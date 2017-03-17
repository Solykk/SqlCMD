package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class Tables implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public Tables(DatabaseManager manager, ViewService viewService, TablePrinter tablePrinter) {
        this.manager = manager;
        this.viewService = viewService;
        this.tablePrinter = tablePrinter;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equalsIgnoreCase("tables");
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
