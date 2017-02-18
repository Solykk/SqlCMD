package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class ReadQuery implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private ViewService viewService;

    public ReadQuery(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("readQuery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwo(command);

        try {
            view.printTable(manager.readQuery(query));
            viewService.readQueryComTry(query);
        } catch (SQLException | NullPointerException e) {
            viewService.readQueryComCatch(query, e.getMessage());
        }
    }
}
