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
public class Insert implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 4);

        String tableName = data[1];
        boolean isKey = false;
        ArrayList<String[]> settings = getSettings(data, isKey);

        view.addHistory("Добавление данных в таблицу: " + tableName + " по критериям " + command + " insert");

        try {
            manager.insert(tableName, settings, isKey);
            view.writeAndHistory("Успех! Данные добавлены", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось добавить данные в  таблицу ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }

    private ArrayList<String[]> getSettings(String[] data, boolean isKey) {
        ArrayList<String[]> settings = new ArrayList<>();
        getKeySet(settings, isKey);
        addSettings(data, settings);
        return settings;
    }

    private void getKeySet(ArrayList<String[]> settings, boolean isKey) {
        String key = keyAction();
        if (key.equalsIgnoreCase("y")) {
            isKey = true;
            view.write("Введите название колонки: ");
            String seqName = view.read();
            settings.add(new String[]{seqName, ""});
        }
    }

    private ArrayList<String[]> addSettings(String[] data, ArrayList<String[]> settings) {
        int index = 2;
        while (index != data.length) {
            SettingsHelper.toSettings(data, settings, index);
            index += 2;
        }
        return settings;
    }

    private String keyAction() {
        view.write("Добавить Seq генератор, если такой имеется? Y/N");
        return view.read();
    }
}
