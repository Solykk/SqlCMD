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
public class Update implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Update(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("update|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 6);

        String tableName = data[1];
        ArrayList<String[]> forUpdate = new ArrayList<>();
        ArrayList<String[]> howUpdate = new ArrayList<>();

        getSettings(data, forUpdate, howUpdate);

        view.addHistory("Обновление содержимого таблицы: " + tableName +
                " по критериям " + command + " update");

        try {
            manager.update(tableName, forUpdate, howUpdate);
            view.writeAndHistory("Успех! Данные обновлены", "\tУспех");

            view.printTable(manager.read(tableName));
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось обновить таблицу ( " + tableName + " ) " + e.getMessage(),
                                 "\tНеудача " + e.getMessage());
        }
    }

    private void getSettings(String[] data, ArrayList<String[]> forUpdate, ArrayList<String[]> howUpdate) {
        int indexFor = 2;
        int indexHow = data.length - (data.length - 2)/2;

        while (indexFor != indexHow){
            SettingsHelper.toSettings(data, forUpdate, indexFor);
            indexFor += 2;
        }
        while (indexHow  != data.length) {
            SettingsHelper.toSettings(data, howUpdate, indexHow);
            indexHow += 2;
        }
    }

}
