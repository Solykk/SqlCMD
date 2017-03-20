package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class ReadQuery implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private Correctly correctly;
    private TablePrinter tablePrinter;

    public ReadQuery(DatabaseManager manager, Services services) {
        this.manager = manager;
        this.viewService = services.getViewService();
        this.correctly = services.getCorrectly();
        this.tablePrinter = services.getTablePrinter();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("readquery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwoCRUD(command);

        try {
            tablePrinter.printTable(manager.readQuery(query));
            viewService.readQueryComTry(query);
        } catch (SQLException | NullPointerException e) {
            viewService.readQueryComCatch(query, e.getMessage());
        }
    }
}
