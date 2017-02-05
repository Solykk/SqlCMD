package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Find implements Command {

    private DatabaseManager manager;
    private View view;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {
        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 2, но есть: " + data.length);
        }

        String tableName = data[1];

        History.cache.add(History.getDate() + " " + "Вывод содержимого таблицы: " + tableName + " " + view.requestTab(TableType.class.getSimpleName().toLowerCase()));

        try {
            Table request = manager.readTable(tableName);
            view.printTable(request);
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось вывести таблицу ( " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
        }

    }
}
