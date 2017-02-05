package ua.com.juja.sqlcmd.view;

import com.sun.xml.internal.ws.api.ha.HaInfo;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.service.History;

import java.util.ArrayList;
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

    @Override
    public void printHistory() {
        for(String string: History.cache){
            System.out.println(string);
        }
    }

    private  void sleeper(int time) {

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private  void stepPrint(String step) {

        char [] result = step.toCharArray();

        for (int i = 0; i < result.length; i++){
            sleeper(100);
            System.out.print(result[i]);
        }
    }

    private  void printTable(Table table){

        if(table == null){
            write("BREAK");
            return;
        }

        ArrayList<Integer> maxLengthCharsOfColumn = new ArrayList<>();
        int sumOfLength = 0;


        for (int j = 0; j < table.getTableDate().size(); j++) {

            String obj;
            if(table.getTableDate().get(j).getValue().size() == 0){
                obj = "";
            } else {
                obj = table.getTableDate().get(j).getValue().get(0);
                if (obj == null){
                    obj = "null";
                }
            }



            Integer tmp = obj.length();
            Integer tmpCol = table.getTableDate().get(j).columnName().length();

            for (int i = 0; i < table.getTableDate().get(j).getValue().size(); i++) {

                String tempObj = table.getTableDate().get(j).getValue().get(i);
                if (tempObj == null){
                    tempObj = "null";
                }

                Integer tempIns = tempObj.length();
                if (tmp < tempIns) {
                    tmp = tempIns;
                }
            }

            if(tmpCol > tmp){
                tmp = tmpCol;
            }

            maxLengthCharsOfColumn.add(tmp);

            sumOfLength += tmp + 3;
        }


        char [] line = new char[sumOfLength + 1];
        for (int q = 0; q < line.length; q++) {
            line[q] = '-';
        }

        char [] reBildName = new char[sumOfLength];
        char [] strReBildName = table.getTableName().toCharArray();

        for (int j = 0; j < reBildName.length - 1; j++) {
            reBildName [j] = ' ';
        }

        int fromToName = (reBildName.length - strReBildName.length)/2;

        for (int j = fromToName, m = 0; m < strReBildName.length; j++, m++) {
            reBildName[j] = strReBildName[m];
        }

        write(new String(line));
        write('|' + new String(reBildName) + '|' + "\n");
        write(new String(line));

        sleeper(5);

        for (int i = 0; i < table.getTableDate().size(); i++){

            String bil = table.getTableDate().get(i).columnName();
            char [] reBild = new char[maxLengthCharsOfColumn.get(i) + 2];
            char [] strReBild = bil.toCharArray();

            for (int j = 0; j < reBild.length; j++) {
                reBild [j] = ' ';
            }

            int fromTo = (reBild.length - strReBild.length)/2;

            for (int j = fromTo, m = 0; m < strReBild.length; j++, m++) {
                reBild[j] = strReBild[m];
            }

            if(i == table.getTableDate().size() - 1){
                System.out.print('|' + new String(reBild) + '|');
            } else {
                System.out.print('|' + new String(reBild));
            }


            sleeper(5);


        }
        write("\n");
        write(new String(line));

        for (int i = 0; i < table.getTableDate().get(0).getValue().size(); i++){

            for (int j = 0; j < table.getTableDate().size(); j++) {

                String bil = table.getTableDate().get(j).getValue().get(i);

                if(bil == null){
                    bil = "null";
                }

                char [] reBild = new char[maxLengthCharsOfColumn.get(j) + 2];
                char [] strReBild = bil.toCharArray();

                for (int w = 0; w < reBild.length; w++) {
                    reBild [w] = ' ';
                }

                int fromTo = (reBild.length - strReBild.length)/2;

                for (int f = fromTo, m = 0; m < strReBild.length; f++, m++) {
                    reBild[f] = strReBild[m];
                }

                if(j == table.getTableDate().size() - 1){
                    System.out.print('|' + new String(reBild) + '|');
                } else{
                    System.out.print('|' + new String(reBild));
                }

                sleeper(5);

            }
            write("\n");
        }

        write(new String(line));
    }
}
