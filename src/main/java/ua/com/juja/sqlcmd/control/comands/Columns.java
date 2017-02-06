package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Columns implements Command {

    private DatabaseManager manager;
    private View view;

    public Columns(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("columns|");
    }

    @Override
    public void process(String command) {

        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 2, но есть: " + data.length);
        }
        String tableName = data[1];
        History.cache.add(History.getDate() + " " + "Вывод содержимого таблицы: " + tableName + " " + Columns.class.getSimpleName().toLowerCase());

        try {
            Table request = manager.getAllColumnNamesFromTable(tableName);
            view.printTable(request);
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не могу осуществить вывод всех колонок таблицы " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
        }
    }
}
