package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class Update implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private SettingsHelper settingsHelper;
    private ViewService viewService;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.settingsHelper = new SettingsHelper();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 6);

        String tableName = data[1];
        ArrayList<String[]> forUpdate = new ArrayList<>();
        ArrayList<String[]> howUpdate = new ArrayList<>();

        settingsHelper.getSetUpdate(data, forUpdate, howUpdate);

        try {
            manager.update(tableName, forUpdate, howUpdate);
            view.printTable(manager.read(tableName));

            viewService.updateComTry(tableName, command);
        } catch (SQLException | NullPointerException e) {
            viewService.updateComCatch(tableName, command, e.getMessage());
        }
    }
}
