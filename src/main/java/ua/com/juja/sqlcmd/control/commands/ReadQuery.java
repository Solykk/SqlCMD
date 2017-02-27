package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class ReadQuery implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public ReadQuery(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
        this.tablePrinter = new TablePrinter(view);
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
