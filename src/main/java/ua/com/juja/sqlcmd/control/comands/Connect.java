package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

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
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {

        History.cache.add(History.getDate() + " " + "Попытка подключиться к базе данных "
                + view.yellowText(Connect.class.getSimpleName().toLowerCase()));

        try {

                String[] data = command.split("\\|");
                if (data.length != 3) {
                    throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                            "ожидается 3, но есть: " + data.length);
                }
                String userName = data[1];
                String password = data[2];

                manager.connect(userName, password);

            History.cache.add(view.requestTab(view.blueText("Успех")));

            view.write("\t\t\t\t\t" + view.blueText("Успех, вы подключились к базе данных:"));
            view.write(view.blueText("Oracle Database - Production"));

        } catch (SQLException e) {
            view.write("Не удалось подключиться к базе данных " + view.redText(e.getMessage()));
            History.cache.add(History.getDate() + " " + "Не удалось подключиться к базе данных " + view.redText(e.getMessage()));
        }

    }
}
