package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

public class Clear implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            manager.clear(tableName);
            viewService.clearComTry(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.clearComCatch(tableName, e.getMessage());
        }
    }
}
