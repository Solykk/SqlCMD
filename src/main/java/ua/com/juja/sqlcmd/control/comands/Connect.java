package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Connect implements Command {

    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.contains("connect|");
    }

    @Override
    public void process(String command) {

        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length != 2) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', ожидается 2, но есть: " + data.length);
                }
                String userName = data[0];
                String password = data[1];

                manager.connect(userName, password);
                break;
            } catch (Exception e) {
                view.write(History.getDate() + " " + "Не удалось подключиться к базе данных " + e.getMessage());
                History.cache.add(History.getDate() + " " + "Не удалось подключиться к базе данных " + e.getMessage());
            }
        }

        view.write("\t\t\t\t\t\t\t\tУспех, вы подключились к базе данных:");
        view.write("Oracle Database 10g Express Edition Release 10.2.0.1.0 - Production");

        History.cache.add(History.getDate() + " " + "Вы подключились к базе данных: Oracle Database 10g Express Edition Release 10.2.0.1.0 - Production");
    }
}
