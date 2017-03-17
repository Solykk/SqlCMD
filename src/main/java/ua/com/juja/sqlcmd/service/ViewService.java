package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

public class ViewService {

    private View view;

    public void setView(View view) {
        this.view = view;
    }


    public void greeting(){
        view.write("\tВас приветствует приложение SqlCMD\n" +
                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password");
        view.addHistory("Запуск приложения");
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

    public void connectComTry(){
        view.addHistory("Попытка подключиться к базе данных connect");
        view.writeAndHistory("Успех, вы подключились к базе данных: Oracle Database - Production", "\tУспех");
    }
    public void connectComCatch(String message){
        view.addHistory("Попытка подключиться к базе данных connect");
        view.writeAndHistory("Не удалось подключиться к базе данных " + message,
                "\tНе удалось подключиться к базе данных " + message);
    }

    public void cudQueryComTry(String query){
        view.addHistory("Выполнение SQL запроса: " + query + " cudQuery");
        view.writeAndHistory("Успех! Запрос выполнен", "\tУспех");
    }
    public void cudQueryComCatch(String query, String message){
        view.addHistory("Выполнение SQL запроса: " + query + " cudQuery");
        view.writeAndHistory("Ошибка. Не удалось выполнить ваш запрос ( " + query + " ) "
                + message, "\tНеудача " + message);
    }

    public void deleteComTry(String tableName){
        view.addHistory("Попытка удалить , по критериям,запись в таблице: " + tableName + " delete");
        view.writeAndHistory("Успех! запись была удалена", "\tУспех");
    }
    public void deleteComCatch(String tableName, String message){
        view.addHistory("Попытка удалить , по критериям,запись в таблице: " + tableName + " delete");
        view.writeAndHistory("Ошибка. Не удалось удалить запись в таблице ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void dropComTry(String tableName){
        view.addHistory("Попытка удалить таблицу: " + tableName + " drop");
        view.writeAndHistory("Успех! Таблица удалена", "\tУспех");
    }
    public void dropComCatch(String tableName, String message){
        view.addHistory("Попытка удалить таблицу: " + tableName + " drop");
        view.writeAndHistory("Ошибка. Не удалось удалить таблицу: ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void findComTry(String tableName){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " find");
        view.writeAndHistory("", "\tУспех");
    }
    public void findComCatch(String tableName, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " find");
        view.writeAndHistory("Ошибка. Не удалось вывести таблицу ( " + tableName + " ) " + message,
                "\tНеудача " + message);
    }

    public void findSetComTry(String tableName, String command){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " по критериям " + command + " findsettings");
        view.writeAndHistory("", "\tУспех");
    }
    public void findSetComCatch(String tableName, String command, String message){
        view.addHistory("Вывод содержимого таблицы: " + tableName + " по критериям " + command + " findsettings");
        view.writeAndHistory("Ошибка. Не удалось по критериям вывести таблицу ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void insertComTry(String tableName, String command){
        view.addHistory("Добавление данных в таблицу: " + tableName + " по критериям " + command + " insert");
        view.writeAndHistory("Успех! Данные добавлены", "\tУспех");
    }
    public void insertComCatch(String tableName, String command, String message){
        view.addHistory("Добавление данных в таблицу: " + tableName + " по критериям " + command + " insert");
        view.writeAndHistory("Ошибка. Не удалось добавить данные в  таблицу ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void readQueryComTry(String query){
        view.addHistory("Вывод SQL запроса: " + query + " readQuery");
        view.writeAndHistory("", "\tУспех");
    }
    public void readQueryComCatch(String query, String message){
        view.addHistory("Вывод SQL запроса: " + query + " readQuery");
        view.writeAndHistory("Ошибка. Не удалось выполнить ваш запрос ( " + query + " ) "
                + message, "\tНеудача " + message);
    }

    public void tablesComTry(){
        view.addHistory("Вывод имен всех пользовательских таблиц tables");
        view.writeAndHistory("", "\tУспех");
    }
    public void tablesComCatch(String message){
        view.addHistory("Вывод имен всех пользовательских таблиц tables");
        view.writeAndHistory("Ошибка. Не могу осуществить вывод всех таблиц " + message,
                "\tНеудача " + message);
    }

    public void tablesTypeComTry(String tableName){
        view.addHistory("Определение типа данных содержащийся в таблице: " + tableName + " tabletype");
        view.writeAndHistory("", "\tУспех");
    }
    public void tablesTypeComCatch(String tableName, String message){
        view.addHistory("Определение типа данных содержащийся в таблице: " + tableName + " tabletype");
        view.writeAndHistory("Ошибка. Не удалось определить тип данных колонок в таблице ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void updateComTry(String tableName, String command){
        view.addHistory("Обновление содержимого таблицы: " + tableName +
                " по критериям " + command + " update");
        view.writeAndHistory("Успех! Данные обновлены", "\tУспех");
    }
    public void updateComCatch(String tableName, String command, String message){
        view.addHistory("Обновление содержимого таблицы: " + tableName +
                " по критериям " + command + " update");
        view.writeAndHistory("Ошибка. Не удалось обновить таблицу ( " + tableName + " ) " + message,
                "\tНеудача " + message);
    }

    public void fileTabComTrySuc(String tableName){
        view.addHistory("Запись содержимого таблицы: " + tableName + " в файл filetable");
        view.writeAndHistory("Данные сохранены в файл", "\tУспех");
    }
    public void fileTabComTryAbort(String tableName){
        view.addHistory("Запись содержимого таблицы: " + tableName + " в файл filetable");
        view.writeAndHistory("", "\tЗапись отменена");
    }
    public void fileTabComCatch(String tableName, String message){
        view.addHistory("Запись содержимого таблицы: " + tableName + " в файл filetable");
        view.writeAndHistory("Ошибка. Не удалось сохранить таблицу ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

    public void createComTry(String tableName, String command){
        view.addHistory("Создание таблицы: " + tableName + " по критериям " + command + " create");
        view.writeAndHistory("Успех! Таблица создана", "\tУспех");
    }
    public void createComCatch(String tableName, String command, String message){
        view.addHistory("Создание таблицы: " + tableName + " по критериям " + command + " create");
        view.writeAndHistory("Ошибка. Не удалось создать таблицу ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }
    public void createPKComTry(String tableName, String columnName){
        view.addHistory("Создание первичного ключа для таблицы: ( " + tableName + " ) по критериям ( "
                + columnName + " ) create");
        view.writeAndHistory("Успех! Первичный ключ создан", "\tУспех");
    }
    public void createPKComCatch(String tableName, String columnName, String message){
        view.addHistory("Создание первичного ключа для таблицы: ( " + tableName + " ) по критериям ( "
                + columnName + " ) create");
        view.writeAndHistory("Ошибка. Не удалось создать первичный ключ ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }
    public void createSeqComTry(String tableName, String columnName){
        view.addHistory("Создание Sequence генератора для таблицы: ( " + tableName + " ) по критериям ( "
                + columnName + " ) create");
        view.writeAndHistory("Успех! Sequence генератор создан", "\tУспех");
    }
    public void createSeqComCatch(String tableName, String columnName, String message){
        view.addHistory("Создание Sequence генератора для таблицы: ( " + tableName + " ) по критериям ( "
                + columnName + " ) create");
        view.writeAndHistory("Ошибка. Не удалось создать Sequence генератор ( " + tableName + " ) "
                + message, "\tНеудача " + message);
    }

}
