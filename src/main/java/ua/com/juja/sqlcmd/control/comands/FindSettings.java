package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 05.02.2017.
 */
public class FindSettings implements Command {

    private DatabaseManager manager;
    private View view;

    public FindSettings(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("findsettings|");
    }

    @Override
    public void process(String command) {
        String [] data = command.split("\\|");
        if(data.length < 4){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается минимум 4, но есть: " + data.length);
        } else if(data.length%2 != 0){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается четное количество аргументов, но есть: " + data.length);
        }

        String tableName = data[1];
        ArrayList<String[]> settings = new ArrayList<>();
        int index = 2;
        while (index != data.length){
            String[] tmp = new String[2];
            tmp[0] = data[index];
            tmp[1] = data[index + 1];
            settings.add(tmp);
            index += 2;
        }

        History.cache.add(History.getDate() + " " + "Вывод содержимого таблицы: " + tableName + " по критериям " + command + " "+ view.requestTab(TableType.class.getSimpleName().toLowerCase()));

        try {
            Table request = manager.read(tableName, settings);
            view.printTable(request);
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось по критериям вывести таблицу ( " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
        }
    }
}
