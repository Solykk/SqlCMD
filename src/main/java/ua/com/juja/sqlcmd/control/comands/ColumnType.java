package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class ColumnType implements Command {

    private DatabaseManager manager;
    private View view;

    public ColumnType(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("columntype|");
    }

    @Override
    public void process(String command) {
        String [] data = command.split("\\|");
        if(data.length != 3){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: " + data.length);
        }
        String tableName = data[1];
        String columnName = data[2];

        History.cache.add(History.getDate() + " " + "Определение типа данных содержащийся в таблице: " + tableName + " у колонки " + columnName + " " + view.requestTab(TableType.class.getSimpleName().toLowerCase()));

        try {
            Table request = manager.getDataTypeColumnFromTable(tableName, columnName);
            view.printTable(request);
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException |  NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось определить тип данных колоноки " + columnName + " в таблице ( " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
        }
    }
}
