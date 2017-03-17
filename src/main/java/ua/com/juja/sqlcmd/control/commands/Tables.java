package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class Tables implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public Tables(DatabaseManager manager, Services services) {
        this.manager = manager;
        this.viewService = services.getViewService();
        this.tablePrinter = services.getTablePrinter();
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
