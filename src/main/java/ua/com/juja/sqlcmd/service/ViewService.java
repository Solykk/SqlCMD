package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

public class ViewService {

    private View view;

    public ViewService(View view){
        this.view = view;
    }

    public void clearComTry(String tableName){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " clear");
        view.writeAndHistory("Успех! Таблица была очищена", "\tУспех");
    }
    public void clearComCatch(String tableName, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " clear");
        view.writeAndHistory("Ошибка. Не удалось очистить таблицу ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void columnsComTry(String tableName){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " columns");
        view.writeAndHistory("", "\tУспех");
    }
    public void columnsComCatch(String tableName, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " columns");
        view.writeAndHistory("Ошибка. Не могу осуществить вывод всех колонок таблицы ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void columnTypeComTry(String tableName, String columnName){
        view.addHistory("Определение типа данных содержащийся в таблице: " + tableName
                + " у колонки " + columnName + " columntype");
        view.writeAndHistory("", "\tУспех");
    }
    public void columnTypComCatch(String tableName, String columnName, String message){
        view.addHistory("Определение типа данных содержащийся в таблице: " + tableName
                + " у колонки " + columnName + " columntype");
        view.writeAndHistory("Ошибка. Не удалось определить тип данных колоноки ( " + columnName
                + " ) в таблице ( " + tableName + " ) " + message, "\tНеудача " + message);
    }



}
