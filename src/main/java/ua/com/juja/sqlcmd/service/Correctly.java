package ua.com.juja.sqlcmd.service;

public class Correctly {

    public String expectedTwo(String command) {
        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается 2, но есть: " + data.length);
        }
        return data[1];
    }

    public String[] expectedThree(String command) {
        String[] data = command.split("\\|");
        if (data.length != 3) {
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается 3, но есть: " + data.length);
        }
        return data;
    }

    public String[] expectedThreeMin(String command) {
        String [] data = command.split("\\|");
        if(data.length < 3){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается минимум 3, но есть: " + data.length);
        }
        return data;
    }

    public String[] expectedMinEven(String command, int parametrs) {
        String [] data = command.split("\\|");
        if(data.length < 4){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается минимум " + parametrs + ", но есть: " + data.length);
        } else if(data.length%2 != 0){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается четное количество аргументов, но есть: " + data.length);
        }
        return data;
    }
}
