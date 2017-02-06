package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class ReadQuery implements Command {

    private DatabaseManager manager;
    private View view;

    public ReadQuery(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("readQuery|");
    }

    @Override
    public void process(String command) {
        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается минимум 2, но есть: " + data.length);
        }

        String query = data[1];

        History.cache.add(History.getDate() + " " + "Вывод SQL запроса: " + query + " " + view.requestTab(TableType.class.getSimpleName().toLowerCase()));

        try {
            Table request = manager.readQuery(query);
            view.printTable(request);
            History.cache.add(view.requestTab(view.blueText("Успех")));
        } catch (SQLException | NullPointerException e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось выполнить ваш запрос ( " + query + " ( " + view.redText(e.getMessage()) + " )"));
        }
    }
}
