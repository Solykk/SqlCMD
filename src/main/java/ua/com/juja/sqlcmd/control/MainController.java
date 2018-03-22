package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.commands.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainController {

    private View view;
    private List<Command> commands;
    private ViewService viewService;
    private final WhileCTRL whileCTRL = new WhileCTRL();

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        Services services = new Services();
        services.setView(view);
        this.viewService = services.getViewService();
        this.commands = new CommandsList(view, manager, whileCTRL, services).getCommands();
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