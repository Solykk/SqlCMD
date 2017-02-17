package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class Drop implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Drop(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("drop|");

    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        view.addHistory("Попытка удалить таблицу: " + tableName + " drop");

        try {
            manager.drop(tableName);
            view.writeAndHistory("Успех! Таблица удалена", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось удалить таблицу: ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
