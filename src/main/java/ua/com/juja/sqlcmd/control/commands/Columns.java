package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class Columns implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private Correctly correctly;
    private TablePrinter tablePrinter;

    public Columns(DatabaseManager manager, Services services) {
        this.manager = manager;
        this.viewService = services.getViewService();
        this.correctly = services.getCorrectly();
        this.tablePrinter = services.getTablePrinter();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("columns|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            tablePrinter.printTable(manager.getColumnNames(tableName));
            viewService.columnsComTry(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.columnsComCatch(tableName, e.getMessage());
        }
    }
}