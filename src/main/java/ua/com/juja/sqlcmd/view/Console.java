package ua.com.juja.sqlcmd.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Console implements View {

    private List<String> history;

    public Console(){
        this.history = new ArrayList<>();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void printHistory() {
        history.forEach(this::write);
    }

    @Override
    public void writeAndHistory(String toWrite, String toHistory) {
        if(toWrite != null && !toWrite.equals("")){
            write(toWrite);
        }
        history.add(toHistory);
    }

    @Override
    public void addHistory(String toHistory) {
        history.add(getDate() + " " + toHistory);
    }

    private String getDate(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }
}