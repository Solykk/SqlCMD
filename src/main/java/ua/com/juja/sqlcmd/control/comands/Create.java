package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Create implements Command {

    private DatabaseManager manager;
    private View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {

        String [] data = command.split("\\|");
        if(data.length < 3){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается минимум 3, но есть: " + data.length);
        }

        String tableName = data[1];
        ArrayList<String> settings = new ArrayList<>();

        int index = 2;

        while (index  != data.length){
            settings.add(data[index]);
            index++;
        }

        History.cache.add(History.getDate() + " " + "Создание таблицы: " + tableName + " по критериям " + command + " "+ view.requestTab(Create.class.getSimpleName().toLowerCase()));

        try {
            manager.createTableWithoutPK(tableName, settings);
            view.write(view.blueText("Успех! Таблица создана"));
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось создать таблицу ( " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
        }

        view.write(view.blueText("Присвоить колонке первичный ключ, если такой имеется? Если да, введите " + view.greenText("y")));
        String key = view.read();
        if (key.equals("y")) {
            view.write(view.blueText("Введите название колонки, которой хотите присвоить ключ "));
            String columnName = view.read();
            History.cache.add(History.getDate() + " " + "Создание первичного ключа для таблицы: " + tableName + " по критериям " + columnName + " "+ view.requestTab(Create.class.getSimpleName().toLowerCase()));

            try {
                manager.createTableCreatePK(tableName, columnName);
                view.write(view.blueText("Успех! Первичный ключ создан"));
                History.cache.add(view.requestTab(view.blueText("Успех")));
            } catch (SQLException | NullPointerException e) {
                History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
                view.write(view.redText("Ошибка. Не удалось создать первичный ключ ( " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
            }

            view.write(view.blueText("Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него. Хотите это сделать? "));
            view.write(view.blueText("Если да, введите " + view.greenText("y")));

            String seq = view.read();
            if(seq.equals("y")){
                History.cache.add(History.getDate() + " " + "Создание Sequence генератора для таблицы: " + tableName + " по критериям " + columnName + " "+ view.requestTab(Create.class.getSimpleName().toLowerCase()));
                view.write(view.blueText("Введите значени с которого будет начинаться отсчет "));

                Long startWith = Long.valueOf(view.read());
                try {
                    manager.createTableSequenceForPK(tableName, startWith);
                    view.write(view.blueText("Успех! Первичный ключ создан"));
                    History.cache.add(view.requestTab(view.blueText("Успех")));
                } catch (SQLException | NullPointerException e) {
                    History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
                    view.write(view.redText("Ошибка. Не удалось создать Sequence генератор ( " + tableName + " ( " + view.redText(e.getMessage()) + " )"));
                }
            }else {
                return;
            }
        } else {
            return;
        }
    }
}
