package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.Table;

/**
 * Created by Solyk on 26.01.2017.
 */
public interface View {

        void write(String massege);
        String read();
        void sleeper(int time);
        void stepPrint(String step);
        String printTable(Table table);

        String redText(String text);
        String greenText(String text);
        String yellowText(String text);
        String blueText(String text);

        String requestTab(String string);

}
