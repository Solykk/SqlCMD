package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

public class ViewImpl implements View {

    private String out;
    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public ViewImpl(){
        this.out = "";
        this.history = "";
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    @Override
    public void write(String message) {
        out += message + "\n";
    }

    @Override
    public String read() {
        return null;
    }

    @Override
    public void printHistory() {
        getHistory();
    }

    @Override
    public void writeAndHistory(String toWrite, String toHistory) {
        if(toWrite != null && !toWrite.equals("")){
            write(toWrite);
        }
        history += toHistory + "\n";
    }

    @Override
    public void addHistory(String toHistory) {
        history += toHistory + "\n";
    }
}
