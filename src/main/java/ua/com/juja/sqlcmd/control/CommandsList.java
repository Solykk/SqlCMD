package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.control.commands.*;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.List;

public class CommandsList {

    private List<Command> commands;

    public CommandsList(View view, DatabaseManager manager, WhileCTRL whileCTRL, Services services){
        this.commands = new ArrayList<>();
        commands.add(new Connect(manager, services));
        commands.add(new Help(view));
        commands.add(new Exit(view, whileCTRL));
        commands.add(new History(view));
        commands.add(new IsConnect(manager, view));
        commands.add(new Tables(manager, services));
        commands.add(new Columns(manager, services));
        commands.add(new TableType(manager, services));
        commands.add(new ColumnType(manager, services));
        commands.add(new Find(manager, services));
        commands.add(new FileTable(manager, view, services));
        commands.add(new FindSettings(manager, services));
        commands.add(new Clear(manager, services));
        commands.add(new Create(manager, view, services));
        commands.add(new Delete(manager, services));
        commands.add(new Drop(manager, services));
        commands.add(new Insert(manager, view, services));
        commands.add(new Update(manager, services));
        commands.add(new ReadQuery(manager, services));
        commands.add(new CudQuery(manager, services));
        commands.add(new Unsupported(view));
    }

    public List<Command> getCommands() {
        return commands;
    }
}