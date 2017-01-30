package ua.com.juja.sqlcmd.view;

import java.util.Scanner;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Console implements View {


    @Override
    public void write(String massege) {
        System.out.println(massege);
    }

    @Override
    public String read() {
        Scanner scanner =  new Scanner(System.in);
        return scanner.nextLine();
    }
}
