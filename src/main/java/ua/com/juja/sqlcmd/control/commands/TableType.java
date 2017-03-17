package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class TableType implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public TableType(DatabaseManager manager, ViewService viewService, Correctly correctly, TablePrinter tablePrinter) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
        this.tablePrinter = tablePrinter;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("tabletype|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            tablePrinter.printTable(manager.getAllTypeColumns(tableName));
            viewService.tablesTypeComTry(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.tablesTypeComCatch(tableName, e.getMessage());
        }
    }
}
