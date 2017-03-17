package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class Connect implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public Connect(DatabaseManager manager, ViewService viewService, Correctly correctly) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("connect|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedThree(command);

        String userName = data[1];
        String password = data[2];

        try {
            manager.connect(userName, password);
            viewService.connectComTry();
        } catch (SQLException e) {
            viewService.connectComCatch(e.getMessage());
        }
    }
}
