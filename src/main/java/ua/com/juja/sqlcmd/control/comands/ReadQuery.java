package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class ReadQuery implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public ReadQuery(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("readQuery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwo(command);

        view.addHistory("Вывод SQL запроса: " + query + " readQuery");

        try {
            view.printTable(manager.readQuery(query));
            view.writeAndHistory("", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось выполнить ваш запрос ( " + query + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
