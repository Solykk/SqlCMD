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
public class Delete implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Delete(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 4);

        String tableName = data[1];
        ArrayList<String[]> settings = getSettings(data);

        view.addHistory("Попытка удалить , по критериям,запись в таблице: " + tableName + " delete");

        try {
            manager.delete(tableName,settings);
            view.writeAndHistory("Успех! запись была удалена", "\tУспех");
            view.printTable(manager.read(tableName));
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось удалить запись в таблице ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача" + e.getMessage());
        }
    }

    private ArrayList<String[]> getSettings(String[] data) {
        ArrayList<String[]> settings = new ArrayList<>();

        int index = 2;
        while (index != data.length) {
            SettingsHelper.toSettings(data, settings, index);
            index += 2;
        }
        return settings;
    }

}
