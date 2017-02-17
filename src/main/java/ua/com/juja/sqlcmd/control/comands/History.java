package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 29.01.2017.
 */
public class History implements Command {

    private View view;

    public History(View view) {
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return  command.equals("history");
    }

    @Override
    public void process(String command) {
        view.printHistory();
    }
}
