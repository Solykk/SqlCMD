package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class Drop implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public Drop(DatabaseManager manager, ViewService viewService, Correctly correctly) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("drop|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            manager.drop(tableName);
            viewService.dropComTry(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.dropComCatch(tableName, e.getMessage());
        }
    }
}
