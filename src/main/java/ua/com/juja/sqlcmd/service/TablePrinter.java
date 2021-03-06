package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {

    private View view;
    private final static int SLEEP_TIME = 5;

    public void setView(View view) {
        this.view = view;
    }

    public  String  printTable(Table table){

        if(table == null) { return ""; }

        StringBuilder tableString = new StringBuilder();
        List<Integer> maxLength = new ArrayList<>();

        int lengthSum = calculatesLength(table, maxLength);

        char[] line = lineCreator(lengthSum);
        char [] containerTN = new char[lengthSum - 1];
        char [] reBuildTN = table.getTableName().toCharArray();

        reBuilder(containerTN, reBuildTN);
        printLine(tableString, line);
        printTableName(tableString, containerTN);
        printLine(tableString, line);
        printColumnName(table, tableString, maxLength);
        printLine(tableString, line);
        printValue(table, tableString, maxLength);
        printLine(tableString, line);

        return tableString.toString();
    }

    private void printTableName(StringBuilder tableString, char[] containerTN) {
        view.write('|' + new String(containerTN) + '|');
        tableString.append('|').append(new String(containerTN)).append('|').append("\n");
        sleeper(SLEEP_TIME);
    }

    private void printColumnName(Table table, StringBuilder tableString, List<Integer> maxLength) {
        for (int i = 0; i < table.getTableData().size(); i++){

            String columnName = table.getTableData().get(i).columnName();

            char [] containerCN = new char[maxLength.get(i) + 2];
            char [] reBuildCN = columnName.toCharArray();

            reBuilder(containerCN, reBuildCN);

            printData(table, tableString, i, containerCN);
            sleeper(SLEEP_TIME);
        }

        lineBreak(tableString);
    }

    private void printValue(Table table, StringBuilder tableString, List<Integer> maxLength) {
        for (int i = 0; i < table.getTableData().get(0).getValue().size(); i++){

            for (int j = 0; j < table.getTableData().size(); j++) {

                String dataValue = table.getTableData().get(j).getValue().get(i);
                dataValue = nullable(dataValue);

                char [] containerDV = new char[maxLength.get(j) + 2];
                char [] reBuildDV = dataValue.toCharArray();

                reBuilder(containerDV, reBuildDV);

                printData(table, tableString, j, containerDV);
                sleeper(SLEEP_TIME);

            }

            lineBreak(tableString);
        }
    }

    private void printLine(StringBuilder tableString, char[] line) {
        view.write(new String(line));
        tableString.append(new String(line)).append("\n");
    }

    private void printData(Table table, StringBuilder tableString, int index, char[] container) {
        if(index == table.getTableData().size() - 1){
            System.out.print('|' + new String(container) + '|');
            tableString.append('|').append(new String(container)).append('|');
        } else {
            System.out.print('|' + new String(container));
            tableString.append('|').append(new String(container));
        }
    }

    private void lineBreak(StringBuilder tableString) {
        view.write("");
        tableString.append("\n");
    }

    private int calculatesLength(Table table, List<Integer> maxLength) {

        int lengthSum = 0;
        Integer tableNameLength = table.getTableName().length();

        for (int j = 0; j < table.getTableData().size(); j++) {

            String tempDataObject;
            if(table.getTableData().get(j).getValue().size() == 0){
                tempDataObject = "";
            } else {

                tempDataObject = table.getTableData().get(j).getValue().get(0);
                tempDataObject = nullable(tempDataObject);
            }

            Integer dataLength = tempDataObject.length();
            Integer columnNameLength = table.getTableData().get(j).columnName().length();

            for (int i = 0; i < table.getTableData().get(j).getValue().size(); i++) {

                String tempObj = table.getTableData().get(j).getValue().get(i);
                tempObj = nullable(tempObj);

                Integer tempIns = tempObj.length();
                if (dataLength < tempIns) {
                    dataLength = tempIns;
                }
            }

            if(columnNameLength > dataLength){
                dataLength = columnNameLength;
            }
            maxLength.add(dataLength);
            lengthSum += dataLength + 3;
        }

        int opportunityBag = 0;

        for (Integer aMaxLength : maxLength) {
            opportunityBag += aMaxLength;
        }

        if(tableNameLength > opportunityBag){
            lengthSum = tableNameLength + 3;
            maxLength.clear();
            maxLength.add(tableNameLength);
        }

        return lengthSum;
    }

    private char[] lineCreator(int lengthSum) {
        char [] line = new char[lengthSum + 1];
        for (int q = 0; q < line.length; q++) {
            line[q] = '-';
        }
        return line;
    }

    private void reBuilder(char[] container, char[] reBuild) {
        for (int i = 0; i < container.length; i++) {
            container [i] = ' ';
        }

        int fromTo = (container.length - reBuild.length)/2;

        for (int i = fromTo, j = 0; j < reBuild.length; i++, j++) {
            container[i] = reBuild[j];
        }
    }

    private String nullable(String string) {
        return string == null ? "null" : string;
    }

    private void sleeper(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}