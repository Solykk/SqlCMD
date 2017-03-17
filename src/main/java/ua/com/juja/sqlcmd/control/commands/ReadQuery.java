package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class ReadQuery implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public ReadQuery(DatabaseManager manager, ViewService viewService, Correctly correctly, TablePrinter tablePrinter) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
        this.tablePrinter = tablePrinter;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("readquery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwo(command);

        try {
            tablePrinter.printTable(manager.readQuery(query));
            viewService.readQueryComTry(query);
        } catch (SQLException | NullPointerException e) {
            viewService.readQueryComCatch(query, e.getMessage());
        }
    }
}
