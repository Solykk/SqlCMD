package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 26.01.2017.
 */
public class MainController {

    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run(){
        view.write("\t\t\t\t\t\t\t\tВас приветствует приложение SqlCMD");
        view.write("Пожалуйста, введите данные для подключения к базе данных в формате: connect|database|username|password");

    }


}
