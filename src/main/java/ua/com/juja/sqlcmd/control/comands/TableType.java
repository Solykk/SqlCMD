package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class TableType implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public TableType(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("tabletype|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        view.addHistory("Определение типа данных содержащийся в таблице: " + tableName + " tabletype");

        try {
            view.printTable(manager.getAllTypeColumns(tableName));
            view.writeAndHistory("", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось определить тип данных колонок в таблице ( " + tableName + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
