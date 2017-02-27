package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class FindSettings implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private SettingsHelper settingsHelper;
    private ViewService viewService;
    private TablePrinter tablePrinter;

    public FindSettings(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.settingsHelper = new SettingsHelper();
        this.viewService = new ViewService(view);
        this.tablePrinter = new TablePrinter(view);
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
