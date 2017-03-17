package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;
import java.util.ArrayList;

public class FindSettings implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private SettingsHelper settingsHelper;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public FindSettings(DatabaseManager manager, ViewService viewService, Correctly correctly, TablePrinter tablePrinter, SettingsHelper settingsHelper) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
        this.tablePrinter = tablePrinter;
        this.settingsHelper = settingsHelper;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("findsettings|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 4);

        String tableName = data[1];
        ArrayList<String[]> settings = settingsHelper.getSettings(data);

        try {
            tablePrinter.printTable(manager.readSet(tableName, settings));
            viewService.findSetComTry(tableName, command);
        } catch (SQLException | NullPointerException e) {
            viewService.findSetComCatch(tableName, command, e.getMessage());
        }
    }
}
