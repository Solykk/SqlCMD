package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Connect implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("connect|");
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
