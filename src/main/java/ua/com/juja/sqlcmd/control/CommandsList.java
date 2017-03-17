package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.commands.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.SettingsHelper;
import ua.com.juja.sqlcmd.service.TablePrinter;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

public class CommandsList {

    private ArrayList<Command> commands;
    private final Correctly correctly = new Correctly();
    private final TablePrinter tablePrinter = new TablePrinter();
    private final SettingsHelper settingsHelper = new SettingsHelper();

    public CommandsList(View view, DatabaseManager manager, WhileCTRL whileCTRL, ViewService viewService){
        this.tablePrinter.setView(view);
        this.commands = new ArrayList<>();
        commands.add(new Connect(manager, viewService, correctly));
        commands.add(new Help(view));
        commands.add(new Exit(view, whileCTRL));
        commands.add(new History(view));
        commands.add(new IsConnect(manager, view));
        commands.add(new Tables(manager, viewService, tablePrinter));
        commands.add(new Columns(manager, viewService, correctly, tablePrinter));
        commands.add(new TableType(manager, viewService, correctly, tablePrinter));
        commands.add(new ColumnType(manager, viewService, correctly, tablePrinter));
        commands.add(new Find(manager, viewService, correctly, tablePrinter));
        commands.add(new FileTable(manager, view, viewService, correctly, tablePrinter));
        commands.add(new FindSettings(manager, viewService, correctly, tablePrinter, settingsHelper));
        commands.add(new Clear(manager, viewService, correctly));
        commands.add(new Create(manager, view, viewService, correctly, settingsHelper));
        commands.add(new Delete(manager, viewService, correctly, tablePrinter, settingsHelper));
        commands.add(new Drop(manager, viewService, correctly));
        commands.add(new Insert(manager, view, viewService, correctly, settingsHelper));
        commands.add(new Update(manager, viewService, correctly, tablePrinter, settingsHelper));
        commands.add(new ReadQuery(manager, viewService, correctly, tablePrinter));
        commands.add(new CudQuery(manager, viewService, correctly));
        commands.add(new Unsupported(view));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
