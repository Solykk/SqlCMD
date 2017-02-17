package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Clear implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        view.addHistory("Вывод содержимого таблицы: " + tableName + " clear");

        try {
            manager.clear(tableName);
            view.writeAndHistory("Успех! Таблица была очищена", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось очистить таблицу ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
