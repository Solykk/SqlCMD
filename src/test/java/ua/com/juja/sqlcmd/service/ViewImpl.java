package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;

public class ViewImpl implements View {

    private String out;
    private String history;
    private int counter;

    public String getHistory() {
        return history;
    }

    public ViewImpl(){
        this.out = "";
        this.history = "";
        this.counter = 0;
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
        if(counter >= 1){

            return "y";
        }
        counter++;
        return "\n";
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
