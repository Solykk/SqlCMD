package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Delete implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private Correctly correctly;
    private TablePrinter tablePrinter;
    private SettingsHelper settingsHelper;
    private final static int PARAMETERS_COUNT = 4;

    public Delete(DatabaseManager manager, Services services){
        this.manager = manager;
        this.viewService = services.getViewService();
        this.correctly = services.getCorrectly();
        this.tablePrinter = services.getTablePrinter();
        this.settingsHelper = services.getSettingsHelper();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("delete|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, PARAMETERS_COUNT);

        String tableName = data[1];
        List<String[]> settings = settingsHelper.getSettings(data);

        try {
            manager.delete(tableName, settings);
            tablePrinter.printTable(manager.read(tableName));

            viewService.deleteComTry(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.deleteComCatch(tableName, e.getMessage());
        }
    }
}