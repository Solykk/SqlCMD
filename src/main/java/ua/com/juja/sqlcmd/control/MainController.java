package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.comands.*;
import ua.com.juja.sqlcmd.view.View;
import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public class MainController {

    private View view;
    private ArrayList<Command> commands;
    public static boolean WHILE_STOPER;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new ArrayList<>();
        commands.add(new Connect(manager, view));
        commands.add(new Help(view));
        commands.add(new Exit(view));
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
        WHILE_STOPER = true;
        doWork();
    }

    private void doWork() {
        view.stepPrint("\t\t\t\t\tВас приветствует приложение ");
        view.write(view.blueText("SqlCMD"));
        view.stepPrint("Пожалуйста, введите данные для подключения к базе данных в формате: ");
        view.write(view.greenText("connect|username|password"));

        while (WHILE_STOPER) {
            view.write("Введи команду (или " + view.redText("help") + " для помощи):");
            String input = view.read();
            for (Command command : commands) {
                if (command.isProcessed(input)) {
                    try {
                        command.process(input);
                        break;
                    } catch (Exception e){
                        view.write(view.redText("Ошибка ") + e.getMessage());
                    }
                }
            }
        }
    }
}
