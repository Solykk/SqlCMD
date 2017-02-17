package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.comands.*;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

public class MainController {

    private View view;
    private ArrayList<Command> commands;
    private WhileCTRL whileCTRL;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.whileCTRL = new WhileCTRL();
        this.commands = new ArrayList<>();
        commands.add(new Connect(manager, view));
        commands.add(new Help(view));
        commands.add(new Exit(view, whileCTRL));
        commands.add(new IsConnect(manager, view));
        commands.add(new Tables(manager, view));
        commands.add(new Columns(manager, view));
        commands.add(new TableType(manager, view));
        commands.add(new ColumnType(manager, view));
        commands.add(new Find(manager, view));
        commands.add(new FileTable(manager, view));
        commands.add(new FindSettings(manager, view));
        commands.add(new Clear(manager, view));
        commands.add(new Create(manager, view));
        commands.add(new Delete(manager, view));
        commands.add(new Drop(manager, view));
        commands.add(new Insert(manager, view));
        commands.add(new Update(manager, view));
        commands.add(new ReadQuery(manager, view));
        commands.add(new CudQuery(manager, view));
        commands.add(new History(view));
        commands.add(new Unsupported(view));
    }

    public void run() {
        doWork();
    }

    private void doWork() {
        greeting();

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

    private void greeting(){
        view.write("\tВас приветствует приложение SqlCMD\n " +
                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password");
        view.addHistory("Запуск приложения");
    }

    private String forAction(){
        view.write("Введи команду (или help для помощи):");
        return view.read();
    }

}
