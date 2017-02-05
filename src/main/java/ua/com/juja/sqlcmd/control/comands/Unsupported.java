package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 05.02.2017.
 */
public class Unsupported implements Command {

    private View view;

    public Unsupported(View view){
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write(view.redText("Несуществующая команда: " + command));
    }
}
