package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.control.MainController;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;

public class Main {
    public static void main(String[] args) {
        new MainController(new Console(), new JDBCDatabaseManager()).run();
    }
}