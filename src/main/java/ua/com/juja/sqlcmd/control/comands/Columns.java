package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Columns implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Columns(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("columns|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        view.addHistory("Вывод содержимого таблицы: " + tableName + " columns");

        try {
            view.printTable(manager.getColumnNames(tableName));
            view.writeAndHistory("", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не могу осуществить вывод всех колонок таблицы ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
