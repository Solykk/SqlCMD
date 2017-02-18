package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class TableType implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public TableType(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
        this.tablePrinter = new TablePrinter(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("tabletype|");
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
