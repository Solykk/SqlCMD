package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Tables implements Command {

    private DatabaseManager manager;
    private View view;

    public Tables(DatabaseManager manager, View view) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("tables");
    }

    @Override
    public void process(String command) {
        History.cache.add(History.getDate() + " " + "Вывод имен всех пользовательских таблиц "
                + view.yellowText(Tables.class.getSimpleName().toLowerCase()));
        try {
            Table request = manager.getAllTableNames();
            view.printTable(request);
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не могу осуществить вывод всех таблиц  "
                    + view.redText(e.getMessage())));
        }
    }
}
