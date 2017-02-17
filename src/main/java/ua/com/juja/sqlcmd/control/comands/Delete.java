package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class Delete implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private SettingsHelper settingsHelper;

    public Delete(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.settingsHelper = new SettingsHelper();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("delete|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 4);

        String tableName = data[1];
        ArrayList<String[]> settings = settingsHelper.getSettings(data);

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
}
