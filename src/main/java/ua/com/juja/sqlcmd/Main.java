package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.control.MainController;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

public class Main {
    public static void main(String[] args) {

        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();

        MainController controller = new MainController(view, manager);
        controller.run();

    }
}
