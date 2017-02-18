package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

public class Find implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
        this.tablePrinter = new TablePrinter(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("find|");
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
