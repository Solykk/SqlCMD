package ua.com.juja.sqlcmd.service;

public class Correctly {

    public String expectedTwo(String command) {
        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается 2, но есть: " + data.length);
        }
        return data[1].toUpperCase();
    }

    public String expectedTwoCRUD(String command) {
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

        return serveData(data);
    }

    public String[] expectedThreeConnect(String command) {
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

        return serveData(data);
    }

    public String[] expectedMinEven(String command, int parametersCount) {
        String [] data = command.split("\\|");
        if(data.length < parametersCount){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается минимум " + parametersCount + ", но есть: " + data.length);
        } else if(data.length%2 != 0){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается четное количество аргументов, но есть: " + data.length);
        }

        for (int index = 1; index < 3; index++) {
            data[index] = data[index].toUpperCase();
        }

        for (int index = 4; index < data.length; index += 2) {
            data[index] = data[index].toUpperCase();
        }

        return data;
    }

    private String[] serveData(String[] data) {
        for (int index = 1; index < data.length; index++) {
            data[index] = data[index].toUpperCase();
        }
        return data;
    }
}
