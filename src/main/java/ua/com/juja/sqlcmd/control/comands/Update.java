package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Update implements Command {

    private DatabaseManager manager;
    private View view;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {

        String [] data = command.split("\\|");
        if(data.length < 6){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается минимум 6, но есть: " + data.length);
        } else if(data.length%2 != 0){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается четное количество аргументов, но есть: " + data.length);
        }

        String tableName = data[1];
        ArrayList<String[]> settingsForUpdate = new ArrayList<>();
        ArrayList<String[]> settingsHowUpdate = new ArrayList<>();

        int indexForUpdate = 2;
        int indexHowUpdate = (data.length - 2)/2;

        while (indexForUpdate != indexHowUpdate){
            SettingsHelper.toSettings(data, settingsForUpdate, indexForUpdate);
            indexForUpdate += 2;
        }
        while (indexHowUpdate  != data.length) {
            SettingsHelper.toSettings(data, settingsHowUpdate, indexHowUpdate);
            indexHowUpdate += 2;
        }

        History.cache.add(History.getDate() + " " + "Обновление содержимого таблицы: " + tableName +
                " по критериям " + command + " " + view.yellowText(Update.class.getSimpleName().toLowerCase()));

        try {
            manager.update(tableName, settingsForUpdate, settingsHowUpdate);
            view.write(view.blueText("Успех! Данные обновлены"));
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось обновить таблицу ( " + tableName + " ) "
                    + view.redText(e.getMessage())));
        }
    }
}
