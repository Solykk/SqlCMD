package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Console implements View {

    private ArrayList<String> history;

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
        for (String result: history) {
            write(result);
        }
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
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return new SimpleDateFormat("yyyy,MM,dd_(HH:mm:ss)").format(date);
    }

}
