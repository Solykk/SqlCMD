package ua.com.juja.sqlcmd.control.commands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

public class Create implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private SettingsHelper settingsHelper;
    private ViewService viewService;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.settingsHelper = new SettingsHelper();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.toLowerCase().startsWith("create|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedThreeMin(command);

        String tableName = data[1];
        ArrayList<String> settings = settingsHelper.getSetCreate(data);

        try {
            manager.createWithoutPK(tableName, settings);
            viewService.createComTry(tableName, command);

            pkCreator(tableName);
        } catch (SQLException | NullPointerException e) {
            viewService.createComCatch(tableName, command, e.getMessage());
        }
    }

    private void pkCreator(String tableName) {
        String key = keyAction();
        if (key.equalsIgnoreCase("y")) {

            String columnName = nameAction();
            try {
                manager.createCreatePK(tableName, columnName);
                viewService.createPKComTry(tableName, columnName);

                sequenceCreator(tableName, columnName);
            } catch (SQLException | NumberFormatException | NullPointerException e) {
                viewService.createPKComCatch(tableName, columnName, e.getMessage());
            }
        }
    }

    private void sequenceCreator(String tableName, String columnName) {
        String seq = seqAction();
        if(seq.equalsIgnoreCase("y")){

            Long startWith = startWith();

            try {
                manager.createSequencePK(tableName, startWith);
                viewService.createSeqComTry(tableName, columnName);
            } catch (SQLException | NumberFormatException | NullPointerException e) {
                viewService.createSeqComCatch(tableName, columnName, e.getMessage());
            }
        }
    }

    private Long startWith() {
        view.write("Введите значени с которого будет начинаться отсчет ");
        return Long.valueOf(view.read());
    }

    private String seqAction() {
        view.write("Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него.\n" +
                "Хотите это сделать? (Y/N)");
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
