package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Insert implements Command {

    private DatabaseManager manager;
    private View view;
    private ViewService viewService;
    private Correctly correctly;
    private SettingsHelper settingsHelper;
    private final static int PARAMETERS_COUNT = 4;

    public Insert(DatabaseManager manager, View view, Services services) {
        this.manager = manager;
        this.view = view;
        this.viewService = services.getViewService();
        this.correctly = services.getCorrectly();
        this.settingsHelper = services.getSettingsHelper();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("insert|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedMinEven(command, PARAMETERS_COUNT);

        String tableName = data[1];
        boolean isKey = getKeySet();
        List<String[]> settings = getSettings(data, isKey);

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

    private List<String[]> getSettings(String[] data, boolean isKey) {
        List<String[]> settings = new ArrayList<>();
        if(isKey){
            settings.add(new String[]{seqAction(), ""});
        }
        settingsHelper.addSettings(data, settings);
        return settings;
    }
}