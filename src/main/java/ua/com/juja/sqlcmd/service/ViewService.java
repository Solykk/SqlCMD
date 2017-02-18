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

    public void connectTypeComTry(){
        view.addHistory("Попытка подключиться к базе данных connect");
        view.writeAndHistory("Успех, вы подключились к базе данных: Oracle Database - Production", "\tУспех");
    }
    public void connectTypComCatch(String message){
        view.addHistory("Попытка подключиться к базе данных connect");
        view.writeAndHistory("Не удалось подключиться к базе данных " + message,
                "\tНе удалось подключиться к базе данных " + message);
    }

    public void cudQueryTypeComTry(String query){
        view.addHistory("Выполнение SQL запроса: " + query + " cudQuery");
        view.writeAndHistory("Успех! Запрос выполнен", "\tУспех");
    }
    public void cudQueryTypComCatch(String query, String message){
        view.addHistory("Выполнение SQL запроса: " + query + " cudQuery");
        view.writeAndHistory("Ошибка. Не удалось выполнить ваш запрос ( " + query + " ) "
                + message, "\tНеудача " + message);
    }

    public void deleteTypeComTry(String tableName){
        view.addHistory("Попытка удалить , по критериям,запись в таблице: " + tableName + " delete");
        view.writeAndHistory("Успех! запись была удалена", "\tУспех");
    }
    public void deleteTypComCatch(String tableName, String message){
        view.addHistory("Попытка удалить , по критериям,запись в таблице: " + tableName + " delete");
        view.writeAndHistory("Ошибка. Не удалось удалить запись в таблице ( " + tableName + " ) "
                + message, "\tНеудача" + message);
    }

    public void dropTypeComTry(String tableName){
        view.addHistory("Попытка удалить таблицу: " + tableName + " drop");
        view.writeAndHistory("Успех! Таблица удалена", "\tУспех");
    }
    public void dropTypComCatch(String tableName, String message){
        view.addHistory("Попытка удалить таблицу: " + tableName + " drop");
        view.writeAndHistory("Ошибка. Не удалось удалить таблицу: ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void findTypeComTry(String tableName){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " find");
        view.writeAndHistory("", "\tУспех");
    }
    public void findTypComCatch(String tableName, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " find");
        view.writeAndHistory("Ошибка. Не удалось вывести таблицу ( " + tableName + " ) " + message,
                "\tНеудача " + message);
    }

}
