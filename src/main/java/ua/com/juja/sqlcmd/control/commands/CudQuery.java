package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;

import java.sql.SQLException;

public class CudQuery implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public CudQuery(DatabaseManager manager, ViewService viewService, Correctly correctly) {
        this.manager = manager;
        this.viewService = viewService;
        this.correctly = correctly;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("cudquery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwo(command);

        try {
            manager.cudQuery(query);
            viewService.cudQueryComTry(query);
        } catch (SQLException | NullPointerException e) {
            viewService.cudQueryComCatch(query, e.getMessage());
        }
    }
}
