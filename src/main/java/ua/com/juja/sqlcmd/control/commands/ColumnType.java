package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class ColumnType implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public ColumnType(DatabaseManager manager, ViewService viewService, Correctly correctly, TablePrinter tablePrinter) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
        this.tablePrinter = tablePrinter;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("columntype|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedThree(command);

        String tableName = data[1];
        String columnName = data[2];

        try {
            tablePrinter.printTable(manager.getTypeColumn(tableName, columnName));
            viewService.columnTypeComTry(tableName, columnName);
        } catch (SQLException |  NullPointerException e) {
            viewService.columnTypComCatch(tableName, columnName, e.getMessage());
        }
    }
}
