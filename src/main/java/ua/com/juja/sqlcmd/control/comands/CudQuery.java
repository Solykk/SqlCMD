package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

public class CudQuery implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public CudQuery(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("cudQuery|");
    }

    @Override
    public void process(String command) {

        String query = correctly.expectedTwo(command);

        view.addHistory("Выполнение SQL запроса: " + query + " cudQuery");

        try {
            manager.cudQuery(query);
            view.writeAndHistory("Успех! Запрос выполнен", "\tУспех");
        } catch (SQLException | NullPointerException e) {
            view.writeAndHistory("Ошибка. Не удалось выполнить ваш запрос ( " + query + " ) "
                    + e.getMessage(), "\tНеудача " + e.getMessage());
        }
    }
}
