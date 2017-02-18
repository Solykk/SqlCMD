package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Drop implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public Drop(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("drop|");

    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            manager.drop(tableName);
            viewService.dropTypeComTry(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.dropTypComCatch(tableName, e.getMessage());
        }
    }
}
