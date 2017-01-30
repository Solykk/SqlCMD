package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Help {

    private View view = new Console();

    public Help(){
        view.write("\t\t\t\t\t\tПривет, я помошник");
        view.write("Существует следующий список возможностей: ");
        view.write("Привет, я помошник");
        view.write("Привет, я помошник");
        view.write("Привет, я помошник");
        view.write("Привет, я помошник");

    }
}
