package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {

        view.addHistory("Вывод имен всех пользовательских таблиц tables");

        try {
            view.printTable(manager.getTableNames());
            view.writeAndHistory("", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не могу осуществить вывод всех таблиц " + e.getMessage(),
                    "\tНеудача " + e.getMessage());
        }
    }
}
