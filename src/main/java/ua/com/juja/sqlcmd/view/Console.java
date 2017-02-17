package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.Table;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Console implements View {

    private ArrayList<String> cache;

    public Console(){
        this.cache = new ArrayList<>();
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
    public  String  printTable(Table table){

        if(table == null){
            return "";
        }
        StringBuilder stringInString = new StringBuilder();
        ArrayList<Integer> maxLengthCharsOfColumn = new ArrayList<>();
        int sumOfLength = 0;

        for (int j = 0; j < table.getTableDate().size(); j++) {
            String tempDataObject;
            if(table.getTableDate().get(j).getValue().size() == 0){
                tempDataObject = "";
            } else {
                tempDataObject = table.getTableDate().get(j).getValue().get(0);
                if (tempDataObject == null){
                    tempDataObject = "null";
                }
            }

            Integer dataLength = tempDataObject.length();
            Integer columnNameLength = table.getTableDate().get(j).columnName().length();

            for (int i = 0; i < table.getTableDate().get(j).getValue().size(); i++) {
                String tempObj = table.getTableDate().get(j).getValue().get(i);
                if (tempObj == null){
                    tempObj = "null";
                }

                Integer tempIns = tempObj.length();
                if (dataLength < tempIns) {
                    dataLength = tempIns;
                }
            }

            if(columnNameLength > dataLength){
                dataLength = columnNameLength;
            }

            maxLengthCharsOfColumn.add(dataLength);
            sumOfLength += dataLength + 3;
        }

        char [] line = new char[sumOfLength + 1];
        for (int q = 0; q < line.length; q++) {
            line[q] = '-';
        }

        char [] containerForReBuildTableName = new char[sumOfLength - 1];//without 1
        char [] reBuildingTableName = table.getTableName().toCharArray();

        for (int k = 0; k < containerForReBuildTableName.length; k++) {//with -1
            containerForReBuildTableName [k] = ' ';
        }

        int fromToName = (containerForReBuildTableName.length - reBuildingTableName.length)/2;

        for (int j = fromToName, m = 0; m < reBuildingTableName.length; j++, m++) {
            containerForReBuildTableName[j] = reBuildingTableName[m];
        }

        write(new String(line));
        stringInString.append(new String(line) + "\n");
        write('|' + new String(containerForReBuildTableName) + '|');
        stringInString.append('|' + new String(containerForReBuildTableName) + '|' + "\n");
        write(new String(line));
        stringInString.append(new String(line) + "\n");

        sleeper(5);

        for (int i = 0; i < table.getTableDate().size(); i++){

            String columnName = table.getTableDate().get(i).columnName();
            char [] containerForReBuildColName = new char[maxLengthCharsOfColumn.get(i) + 2];
            char [] reBuildingColName = columnName.toCharArray();

            for (int k = 0; k < containerForReBuildColName.length; k++) {
                containerForReBuildColName [k] = ' ';
            }

            int subStrFromTo = (containerForReBuildColName.length - reBuildingColName.length)/2;

            for (int j = subStrFromTo, m = 0; m < reBuildingColName.length; j++, m++) {
                containerForReBuildColName[j] = reBuildingColName[m];
            }

            if(i == table.getTableDate().size() - 1){
                System.out.print('|' + new String(containerForReBuildColName) + '|');
                stringInString.append('|' + new String(containerForReBuildColName) + '|');
            } else {
                System.out.print('|' + new String(containerForReBuildColName));
                stringInString.append('|' + new String(containerForReBuildColName));
            }

            sleeper(5);

        }

        write("");
        stringInString.append("\n");
        write(new String(line));
        stringInString.append(new String(line) + "\n");

        for (int i = 0; i < table.getTableDate().get(0).getValue().size(); i++){

            for (int j = 0; j < table.getTableDate().size(); j++) {

                String dataValue = table.getTableDate().get(j).getValue().get(i);

                if(dataValue == null){
                    dataValue = "null";
                }

                char [] containerForReBuildDataValue = new char[maxLengthCharsOfColumn.get(j) + 2];
                char [] reBuildingDataValue = dataValue.toCharArray();

                for (int k = 0; k < containerForReBuildDataValue.length; k++) {
                    containerForReBuildDataValue [k] = ' ';
                }

                int fromTo = (containerForReBuildDataValue.length - reBuildingDataValue.length)/2;

                for (int f = fromTo, m = 0; m < reBuildingDataValue.length; f++, m++) {
                    containerForReBuildDataValue[f] = reBuildingDataValue[m];
                }

                if(j == table.getTableDate().size() - 1){
                    System.out.print('|' + new String(containerForReBuildDataValue) + '|');
                    stringInString.append('|' + new String(containerForReBuildDataValue) + '|');
                } else{
                    System.out.print('|' + new String(containerForReBuildDataValue));
                    stringInString.append('|' + new String(containerForReBuildDataValue));
                }

                sleeper(5);

            }

            write("");
            stringInString.append("\n");
        }

        write(new String(line));
        stringInString.append(new String(line) + "\n");

        return stringInString.toString();
    }

    @Override
    public void printHistory() {
        for (String result: cache) {
            write(result);
        }
    }

    @Override
    public void writeAndHistory(String toWrite, String toHistory) {
        if(toWrite != null && !toWrite.equals("")){
            write(toWrite);
        }
        cache.add(toHistory);
    }

    @Override
    public void addHistory(String toHistory) {
        cache.add(getDate() + " " + toHistory);
    }

    private void sleeper(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getDate(){
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return new SimpleDateFormat("yyyy,MM,dd_(HH:mm:ss)").format(date);
    }
}
