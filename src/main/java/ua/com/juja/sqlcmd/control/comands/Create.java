package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Create implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private SettingsHelper settingsHelper;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.settingsHelper = new SettingsHelper();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedThreeMin(command);

        String tableName = data[1];
        ArrayList<String> settings = settingsHelper.getSetCreate(data);

        view.addHistory("Создание таблицы: " + tableName + " по критериям " + command + " create");

        try {
            manager.createWithoutPK(tableName, settings);
            view.writeAndHistory("Успех! Таблица создана", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось создать таблицу ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }

        String key = keyAction();
        if (key.equalsIgnoreCase("y")) {

            String columnName = nameAction();
            view.addHistory("Создание первичного ключа для таблицы: ( " + tableName + " ) по критериям ( "
                    + columnName + " ) create");

            try {
                manager.createCreatePK(tableName, columnName);
                view.writeAndHistory("Успех! Первичный ключ создан", "\tУспех");
            } catch (SQLException | NullPointerException e) {
                view.writeAndHistory("Ошибка. Не удалось создать первичный ключ ( " + tableName + " ) "
                        + e.getMessage(), "\tНеудача " + e.getMessage());
            }

            String seq = seqAction();
            if(seq.equalsIgnoreCase("y")){
                view.addHistory("Создание Sequence генератора для таблицы: ( " + tableName + " ) по критериям ( "
                        + columnName + " ) create");

                Long startWith = startWith();
                try {
                    manager.createSequencePK(tableName, startWith);
                    view.writeAndHistory("Успех! Первичный ключ создан", "\tУспех");
                } catch (SQLException | NullPointerException e) {
                    view.writeAndHistory("Ошибка. Не удалось создать Sequence генератор ( " + tableName + " ) "
                            + e.getMessage(), "\tНеудача " + e.getMessage());
                }
            } else {
                return;
            }
        } else {
            return;
        }
    }

    private Long startWith() {
        view.write("Введите значени с которого будет начинаться отсчет ");
        return Long.valueOf(view.read());
    }

    private String seqAction() {
        view.write("Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него. Хотите это сделать? (Y/N)");
        return view.read();
    }

    private String nameAction() {
        view.write("Введите название колонки, которой хотите присвоить ключ ");
        return view.read();
    }

    private String keyAction() {
        view.write("Присвоить колонке первичный ключ? (Y/N)");
        return view.read();
    }

}
