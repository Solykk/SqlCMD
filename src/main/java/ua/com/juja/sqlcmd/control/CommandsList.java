package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.commands.*;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

public class CommandsList {

    private ArrayList<Command> commands;

    public CommandsList(View view, DatabaseManager manager, WhileCTRL whileCTRL){
        this.commands = new ArrayList<>();
        commands.add(new Connect(manager, view));
        commands.add(new Help(view));
        commands.add(new Exit(view, whileCTRL));
        commands.add(new History(view));
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
        commands.add(new Unsupported(view));
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }
}
