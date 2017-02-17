package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class ColumnType implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public ColumnType(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("columntype|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedThree(command);

        String tableName = data[1];
        String columnName = data[2];

        view.addHistory("Определение типа данных содержащийся в таблице: " + tableName
                + " у колонки " + columnName + " columntype");

        try {
            view.printTable(manager.getTypeColumn(tableName, columnName));
            view.writeAndHistory("", "\tУспех");
        } catch (SQLException |  NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось определить тип данных колоноки ( " + columnName
                    + " ) в таблице ( " + tableName + " ) " + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
