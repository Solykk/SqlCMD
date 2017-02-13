package ua.com.juja.sqlcmd.view;

import ua.com.juja.sqlcmd.model.Table;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Console implements View {

    private String red;
    {
        red = "\033[31;1m";
    }

    private String green;

    {
        green = "\033[32;1m";
    }

    private String yellow;

    {
        yellow = "\033[33;1m";
    }

    private String blue;

    {
        blue = "\033[34;1m";
    }

    private String bre;

    {
        bre = "\033[39;49m";
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
    public  void sleeper(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String redText(String text){return text;}//red + text + bre;
    @Override
    public String greenText(String text){return text;}//green + text + bre;
    @Override
    public String yellowText(String text){return text;}//yellow + text + bre;
    @Override
    public String blueText(String text){return text;}//blue + text + bre;
    @Override
    public String requestTab(String string) {
        return "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + string;
    }
    @Override
    public  void stepPrint(String step) {

        char [] result = step.toCharArray();

        for (int i = 0; i < result.length; i++){
            sleeper(10);
            System.out.print(result[i]);
        }
    }
    @Override
    public  String  printTable(Table table){

        if(table == null){
            write("Table = null");
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

}
