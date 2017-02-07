package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("exit");
    }

    @Override
    public void process(String command) {
        view.write(view.blueText("До встречи!"));
        System.exit(0);
    }

}
