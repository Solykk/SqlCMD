package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewServiceTest {

    private ViewImpl viewImpl;
    private ViewService viewService = new ViewService();

    @Before
    public void start(){
        viewImpl = new ViewImpl();
        viewService.setView(viewImpl);
    }

    @Test
    public void test_greeting(){
        viewService.greeting();
        assertEquals("\tВас приветствует приложение SqlCMD\n" +
                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Запуск приложения\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_clearComTry(){
        viewService.clearComTry("FIRST");
        assertEquals("Успех! Таблица была очищена\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST clear\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_clearComCatch(){
        viewService.clearComCatch("FIRST", "Exception\n");
        assertEquals("Ошибка. Не удалось очистить таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST clear\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_columnsComTry(){
        viewService.columnsComTry("FIRST");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST columns\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_columnsComCatch(){
        viewService.columnsComCatch("FIRST", "Exception\n");
        assertEquals("Ошибка. Не могу осуществить вывод всех колонок таблицы ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST columns\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_columnTypeComTry(){
        viewService.columnTypeComTry("FIRST", "TEST");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Определение типа данных содержащийся в таблице: FIRST у колонки TEST columntype\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_columnTypeComCatch(){
        viewService.columnTypComCatch("FIRST", "TEST", "Exception\n");
        assertEquals("Ошибка. Не удалось определить тип данных колоноки ( TEST ) в таблице ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Определение типа данных содержащийся в таблице: FIRST у колонки TEST columntype\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_connectComTry(){
        viewService.connectComTry();
        assertEquals("Успех, вы подключились к базе данных: Oracle Database - Production\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Попытка подключиться к базе данных connect\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_connectComCatch(){
        viewService.connectComCatch("Exception\n");
        assertEquals("Не удалось подключиться к базе данных Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Попытка подключиться к базе данных connect\n" +
                        "\tНе удалось подключиться к базе данных Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_cudQueryComTry(){
        viewService.cudQueryComTry("SELECT * FROM FIRST");
        assertEquals("Успех! Запрос выполнен\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Выполнение SQL запроса: SELECT * FROM FIRST cudQuery\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_cudQueryComCatch(){
        viewService.cudQueryComCatch("SELECT * FROM FIRST", "Exception\n");
        assertEquals("Ошибка. Не удалось выполнить ваш запрос ( SELECT * FROM FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Выполнение SQL запроса: SELECT * FROM FIRST cudQuery\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_deleteComTry(){
        viewService.deleteComTry("FIRST");
        assertEquals("Успех! запись была удалена\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Попытка удалить , по критериям,запись в таблице: FIRST delete\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_deleteComCatch(){
        viewService.deleteComCatch("FIRST", "Exception\n");
        assertEquals("Ошибка. Не удалось удалить запись в таблице ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Попытка удалить , по критериям,запись в таблице: FIRST delete\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_dropComTry(){
        viewService.dropComTry("FIRST");
        assertEquals("Успех! Таблица удалена\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Попытка удалить таблицу: FIRST drop\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_dropComCatch(){
        viewService.dropComCatch("FIRST", "Exception\n");
        assertEquals("Ошибка. Не удалось удалить таблицу: ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Попытка удалить таблицу: FIRST drop\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_findComTry(){
        viewService.findComTry("FIRST");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST find\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_findComCatch(){
        viewService.findComCatch("FIRST", "Exception\n");
        assertEquals("Ошибка. Не удалось вывести таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST find\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_findSetComTry(){
        viewService.findSetComTry("FIRST", "SETTINGS");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST по критериям SETTINGS findsettings\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_findSetComCatch(){
        viewService.findSetComCatch("FIRST", "SETTINGS", "Exception\n");
        assertEquals("Ошибка. Не удалось по критериям вывести таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод содержимого таблицы: FIRST по критериям SETTINGS findsettings\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_insertComTry(){
        viewService.insertComTry("FIRST", "SETTINGS");
        assertEquals("Успех! Данные добавлены\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Добавление данных в таблицу: FIRST по критериям SETTINGS insert\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_insertComCatch(){
        viewService.insertComCatch("FIRST", "SETTINGS", "Exception\n");
        assertEquals("Ошибка. Не удалось добавить данные в  таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Добавление данных в таблицу: FIRST по критериям SETTINGS insert\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_readQueryComTry(){
        viewService.readQueryComTry("SELECT * FROM FIRST");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод SQL запроса: SELECT * FROM FIRST readQuery\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_readQueryComCatch(){
        viewService.readQueryComCatch("SELECT * FROM FIRST", "Exception\n");
        assertEquals("Ошибка. Не удалось выполнить ваш запрос ( SELECT * FROM FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод SQL запроса: SELECT * FROM FIRST readQuery\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_tablesComTry(){
        viewService.tablesComTry();
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод имен всех пользовательских таблиц tables\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_tablesComCatch(){
        viewService.tablesComCatch("Exception\n");
        assertEquals("Ошибка. Не могу осуществить вывод всех таблиц Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Вывод имен всех пользовательских таблиц tables\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_tablesTypeComTry(){
        viewService.tablesTypeComTry("FIRST");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Определение типа данных содержащийся в таблице: FIRST tabletype\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_tablesTypeComCatch(){
        viewService.tablesTypeComCatch("FIRST","Exception\n");
        assertEquals("Ошибка. Не удалось определить тип данных колонок в таблице ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Определение типа данных содержащийся в таблице: FIRST tabletype\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_updateComTry(){
        viewService.updateComTry("FIRST", "SETTINGS");
        assertEquals("Успех! Данные обновлены\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Обновление содержимого таблицы: FIRST по критериям SETTINGS update\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_updateComCatch(){
        viewService.updateComCatch("FIRST", "SETTINGS","Exception\n");
        assertEquals("Ошибка. Не удалось обновить таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Обновление содержимого таблицы: FIRST по критериям SETTINGS update\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_fileTabComTrySuc(){
        viewService.fileTabComTrySuc("FIRST");
        assertEquals("Данные сохранены в файл\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Запись содержимого таблицы: FIRST в файл filetable\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_fileTabComTryAbort(){
        viewService.fileTabComTryAbort("FIRST");
        assertEquals("",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Запись содержимого таблицы: FIRST в файл filetable\n" +
                        "\tЗапись отменена\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_fileTabComCatch(){
        viewService.fileTabComCatch("FIRST","Exception\n");
        assertEquals("Ошибка. Не удалось сохранить таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Запись содержимого таблицы: FIRST в файл filetable\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_createComTry(){
        viewService.createComTry("FIRST", "SETTINGS");
        assertEquals("Успех! Таблица создана\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Создание таблицы: FIRST по критериям SETTINGS create\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_createComCatch(){
        viewService.createComCatch("FIRST", "SETTINGS","Exception\n");
        assertEquals("Ошибка. Не удалось создать таблицу ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Создание таблицы: FIRST по критериям SETTINGS create\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_createPKComTry(){
        viewService.createPKComTry("FIRST", "TEST");
        assertEquals("Успех! Первичный ключ создан\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Создание первичного ключа для таблицы: ( FIRST ) по критериям ( TEST ) create\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_createPKComCatch(){
        viewService.createPKComCatch("FIRST", "TEST","Exception\n");
        assertEquals("Ошибка. Не удалось создать первичный ключ ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Создание первичного ключа для таблицы: ( FIRST ) по критериям ( TEST ) create\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_createSeqComTry(){
        viewService.createSeqComTry("FIRST", "TEST");
        assertEquals("Успех! Sequence генератор создан\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Создание Sequence генератора для таблицы: ( FIRST ) по критериям ( TEST ) create\n" +
                        "\tУспех\n",
                viewImpl.getHistory());
    }

    @Test
    public void test_createSeqComCatch(){
        viewService.createSeqComCatch("FIRST", "TEST","Exception\n");
        assertEquals("Ошибка. Не удалось создать Sequence генератор ( FIRST ) Exception\n\n",
                viewImpl.getOut());
        viewImpl.setOut("");
        assertEquals("Создание Sequence генератора для таблицы: ( FIRST ) по критериям ( TEST ) create\n" +
                        "\tНеудача Exception\n\n",
                viewImpl.getHistory());
    }

}
