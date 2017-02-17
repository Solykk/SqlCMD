package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;

public class Find implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Find(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("find|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        view.addHistory("Вывод содержимого таблицы: " + tableName + " find");

        try {
            view.printTable(manager.read(tableName));
            view.writeAndHistory("", "\tУспех");
        } catch (Exception e) {
            view.writeAndHistory("Ошибка. Не удалось вывести таблицу ( " + tableName + " ) " + e.getMessage(),
                    "\tНеудача " + e.getMessage());
        }
    }
}
