package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.view.View;
import java.sql.SQLException;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Connect implements Command {

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {

        String[] data = correctly.expectedThree(command);

        String userName = data[1];
        String password = data[2];

        view.addHistory("Попытка подключиться к базе данных connect");

        try {
            manager.connect(userName, password);
            view.writeAndHistory("Успех, вы подключились к базе данных: Oracle Database - Production", "\tУспех");
        } catch (SQLException e) {
            view.writeAndHistory("Не удалось подключиться к базе данных " + e.getMessage(),
                    "\tНе удалось подключиться к базе данных " + e.getMessage());
        }
    }
}
