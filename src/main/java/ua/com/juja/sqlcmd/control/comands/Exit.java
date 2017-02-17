package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.WhileCTRL;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Exit implements Command {

    private View view;
    private WhileCTRL whileCTRL;

    public Exit(View view, WhileCTRL whileCTRL) {
        this.view = view;
        this.whileCTRL = whileCTRL;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write("До встречи!");
        whileCTRL.setValue(false);
    }
}
