package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

public class Find implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public Find(DatabaseManager manager, ViewService viewService, Correctly correctly, TablePrinter tablePrinter) {
        this.manager = manager;
        this.correctly = correctly;
        this.viewService = viewService;
        this.tablePrinter = tablePrinter;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("find|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            tablePrinter.printTable(manager.read(tableName));
            viewService.findComTry(tableName);
        } catch (Exception e) {
            viewService.findComCatch(tableName, e.getMessage());
        }
    }
}
