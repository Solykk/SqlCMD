package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class CudQuery implements Command {

    private DatabaseManager manager;
    private Correctly correctly;
    private ViewService viewService;

    public CudQuery(DatabaseManager manager, View view) {
        this.manager = manager;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("cudQuery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwo(command);

        try {
            manager.cudQuery(query);
            viewService.cudQueryTypeComTry(query);
        } catch (SQLException | NullPointerException e) {
            viewService.cudQueryTypComCatch(query, e.getMessage());
        }
    }
}
