package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Insert implements Command {

    private DatabaseManager manager;
    private View view;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {

        String [] data = command.split("\\|");

        if(data.length < 4){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается минимум 4, но есть: " + data.length);
        } else if(data.length%2 != 0){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается четное количество аргументов, но есть: " + data.length);
        }

        ArrayList<String[]> settings = new ArrayList<>();
        boolean isKey;
        view.write(view.blueText("Добавить Seq генератор, если такой имеется? Если да, введите " + view.greenText("y")));
        String key = view.read();
        if (key.equals("y")) {
            isKey = true;
            view.write(view.blueText("Введите название колонки: "));
            String seqName = view.read();
            settings.add(new String[]{seqName,""});
        } else {
            isKey = false;
        }

        String tableName = data[1];

        int index = 2;
        while (index != data.length) {
            SettingsHelper.toSettings(data, settings, index);
            index += 2;
        }

        History.cache.add(History.getDate() + " " + "Добавление данных в таблицу: " + tableName +
                " по критериям " + command + " " + view.yellowText(Insert.class.getSimpleName().toLowerCase()));

        try {
            manager.insert(tableName, settings, isKey);
            view.write(view.blueText("Успех! Данные добавлены"));
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось добавить данные в  таблицу ( " + tableName + " ) "
                    + view.redText(e.getMessage())));
        }
    }

}
