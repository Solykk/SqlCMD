package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewServiceTest {

    private ViewImpl viewImpl;
    private ViewService viewService;

    @Before
    public void start(){
        viewImpl = new ViewImpl();
        viewService = new ViewService(viewImpl);
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




}
