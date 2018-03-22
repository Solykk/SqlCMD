package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Update implements Command {

    private DatabaseManager manager;
    private ViewService viewService;
    private Correctly correctly;
    private TablePrinter tablePrinter;
    private SettingsHelper settingsHelper;
    private final static int PARAMETERS_COUNT = 6;

    public Update(DatabaseManager manager, Services services) {
        this.manager = manager;
        this.viewService = services.getViewService();
        this.correctly = services.getCorrectly();
        this.tablePrinter = services.getTablePrinter();
        this.settingsHelper = services.getSettingsHelper();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("update|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, PARAMETERS_COUNT);

        String tableName = data[1];
        List<String[]> forUpdate = new ArrayList<>();
        List<String[]> howUpdate = new ArrayList<>();

        settingsHelper.getSetUpdate(data, forUpdate, howUpdate);

        try {
            manager.update(tableName, forUpdate, howUpdate);
            tablePrinter.printTable(manager.read(tableName));

            viewService.updateComTry(tableName, command);
        } catch (SQLException | NullPointerException e) {
            viewService.updateComCatch(tableName, command, e.getMessage());
        }
    }
}