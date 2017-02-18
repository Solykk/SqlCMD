package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class Insert implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private SettingsHelper settingsHelper;
    private ViewService viewService;

    public Insert(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.settingsHelper = new SettingsHelper();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("insert|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, 4);

        String tableName = data[1];
        boolean isKey = getKeySet();
        ArrayList<String[]> settings = getSettings(data, isKey);

        try {
            manager.insert(tableName, settings, isKey);
            viewService.insertComTry(tableName, command);
        } catch (SQLException | NullPointerException e) {
            viewService.insertComCatch(tableName, command, e.getMessage());
        }
    }

    private String keyAction() {
        view.write("Добавить Seq генератор, если такой имеется? Y/N");
        return view.read();
    }

    private boolean getKeySet() {
        String key = keyAction();
        return key.equalsIgnoreCase("y");
    }

    private String seqAction() {
        view.write("Введите название колонки: ");
        return view.read();
    }

    private ArrayList<String[]> getSettings(String[] data, boolean isKey) {
        ArrayList<String[]> settings = new ArrayList<>();
        if(isKey){
            String seqName = seqAction();
            settings.add(new String[]{seqName, ""});
        }
        settingsHelper.addSettings(data, settings);
        return settings;
    }
}