package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

public class ViewService {

    private View view;

    public ViewService(View view){
        this.view = view;
    }

    public void clearComCatch(String tableName, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " clear");
        view.writeAndHistory("Ошибка. Не удалось очистить таблицу ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }
    public void clearComTry(String tableName){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " clear");
        view.writeAndHistory("Успех! Таблица была очищена", "\tУспех");
    }

    public void columnsComCatch(String tableName, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " columns");
        view.writeAndHistory("Ошибка. Не могу осуществить вывод всех колонок таблицы ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }
    public void columnsComTry(String tableName){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " columns");
        view.writeAndHistory("", "\tУспех");
    }


}
