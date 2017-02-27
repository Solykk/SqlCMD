package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.commands.*;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

public class MainController {

    private View view;
    private ArrayList<Command> commands;
    private WhileCTRL whileCTRL;
    private ViewService viewService;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.viewService = new ViewService(view);
        this.whileCTRL = new WhileCTRL();
        this.commands = new CommandsList(view, manager, whileCTRL).getCommands();
    }

    public void run() {
        doWork();
    }

    private void doWork() {
        viewService.greeting();

        while (whileCTRL.getValue()) {
            String input = forAction();

            for (Command command : commands) {
                if (command.isProcessed(input)) {
                    try {
                        command.process(input);
                        break;
                    } catch (Exception e){
                        view.write("Ошибка " + e.getMessage());
                    }
                }
            }
        }
    }

    private String forAction(){
        view.write("Введи команду (или help для помощи):");
        return view.read();
    }

}
