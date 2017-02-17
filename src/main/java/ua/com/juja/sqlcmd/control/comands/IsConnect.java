package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 05.02.2017.
 */
public class IsConnect implements Command {

    private DatabaseManager manager;
    private View view;

    public IsConnect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return !manager.isConnected();
    }

    @Override
    public void process(String command) {
        view.write("Вы не можете пользоваться командами, пока " +
                "не подключитесь с помощью комманды " +
                "connect|username|password");
    }
}
