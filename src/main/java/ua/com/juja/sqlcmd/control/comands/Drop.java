package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Drop implements Command {

    private DatabaseManager manager;
    private View view;

    public Drop(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("drop|");

    }

    @Override
    public void process(String command) {

        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается 2, но есть: " + data.length);
        }
        String tableName = data[1];

        History.cache.add(History.getDate() + " " + "Попытка удалить таблицу: " + tableName
                + " " + view.yellowText(Drop.class.getSimpleName().toLowerCase()));

        try {
            manager.drop(tableName);
            History.cache.add(view.requestTab(view.blueText("Успех")));
            view.write(view.blueText("Успех! Таблица удалена"));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось удалить таблицу: ( " + tableName + " ) "
                    + view.redText(e.getMessage())));
        }
    }
}
