package ua.com.juja.sqlcmd.integration;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ua.com.juja.sqlcmd.Main;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.DropAllHelper;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntegrationTest {

    private  DatabaseManager manager;
    private  ByteArrayOutputStream out;
    private  ConfigurableInputStream in;

    @BeforeClass
    public static void dropAll(){
        DropAllHelper.dropAll();
    }

    @AfterClass
    public static  void purge() {
        DropAllHelper.purgeRecycle();
    }

    @Before
    public void setup() {
        manager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    public String getData() {
            String result = new String(out.toByteArray());
            out.reset();
            return result;
    }

    private String createTable(){
        return "FIRST|TEST VARCHAR2(20 BYTE) NOT NULL|TEST1  NUMBER (10) NULL|" +
                "TEST2 VARCHAR2(10 BYTE) NULL|TEST3 DATE NULL|TEST4 NUMBER(10) NOT NULL";
    }

    @Test
    public void test_Help() {
        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                "\tВас приветствует приложение SqlCMD\n" +
                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "connect\n" +
                "\tКоманда для подключения к соответствующей БД\n" +
                "\tФормат команды: connect|username|password\n" +
                "\tгде: \n" +
                "\t\tusername -  имя пользователя БД (совпвдает с именем БД)\n" +
                "\t\tpassword - пароль пользователя БД\n" +
                "\tФормат вывода: текстовое сообщение с результатом выполнения операции.\r\n" +
                "tables\n" +
                "\tКоманда выводит список всех пользовательских таблиц \n" +
                "\t\tсодержащихся в БД к которой вы подключены.\n" +
                "\tФормат команды: tables\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "columns\n" +
                "\tКоманда выводит список всех колонок  \n" +
                "\t\tсодержащихся в запрашиваемой таблице.\n" +
                "\tФормат команды: columns|tableName\n" +
                "\tгде tableName - имя запрашиваемой таблицы\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "tabletype\n" +
                "\tКоманда выводит список всех колонок, \n" +
                "\t\t\t\t\tтип данных для соответсвующей колонки, \n" +
                "\t\t\t\t\tвозможность содержать null значение соответсвующей колонки\n" +
                "\t\tв запрашиваемой таблице.\n" +
                "\tФормат команды: tabletype|tableName\n" +
                "\tгде tableName - имя запрашиваемой таблицы\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "columntype\n" +
                "\tКоманда выводит  колоноку, \n" +
                "\t\t\t\t\tтип данных для соответсвующей колонки, \n" +
                "\t\t\t\t\tвозможность содержать null значение соответсвующей колонки\n" +
                "\t\tв запрашиваемой таблице.\n" +
                "\tФормат команды: columntype|tableName|columnName\n" +
                "\tгде tableName - имя запрашиваемой таблицы\n" +
                "\t\tcolummnName - имя столбца в запрашиваемой таблице\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "clear\n" +
                "\tКоманда очищает содержимое указанной (всей) таблицы\n" +
                "\tФормат команды: clear|tableName\n" +
                "\tгде tableName - имя очищаемой таблицы\n" +
                "\tФормат вывода: текстовое сообщение с результатом выполнения операции\r\n" +
                "drop\n" +
                "\tКоманда удаляет заданную таблицу\n" +
                "\tФормат команды: drop|tableName\n" +
                "\tгде tableName - имя удаляемой таблицы\n" +
                "\tФормат вывода: текстовое сообщение с результатом выполнения операции\r\n" +
                "create\n" +
                "\tКоманда создает новую таблицу с заданными полями\n" +
                "\tФормат команды: create|tableName|column1(data type(data size)) nullable|column2(data type(data size)) \n" +
                "nullable|...|columnN(data type(data size)) nullable\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\tcolumn1 - имя первого столбца записи\n" +
                "\t\t(data type(data size)) - (тип данных колонки(максимальный размер данных для колонки))\n" +
                "\t\t\tnullable:\n" +
                "\t\t\t\tnull - при создании записи в столбце значение может содержать null\n" +
                "\t\t\t\tnot null - при создании записи в столбце значение НЕ может содержать null\n" +
                "\tcolumn2 - имя второго столбца записи\n" +
                "\t\t-//-\n" +
                "\tcolumnN - имя n-го столбца записи\n" +
                "\t\t-//-\n" +
                "\tФормат вывода: текстовое сообщение с результатом выполнения операции\r\n" +
                "find\n" +
                "\tКоманда для получения содержимого указанной таблицы\n" +
                "\tФормат команды: find|tableName\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "filetable\n" +
                "\tКоманда для сохранения содержимого указанной таблицы в файл\n" +
                "\tФормат команды: filetable|tableName\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\tФормат вывода: табличка в консольном формате + текстовое сообщение с результатом выполнения операции\r\n" +
                "findsettings\n" +
                "\tКоманда для получения содержимого указанной таблицы по определенным критериям\n" +
                "\tФормат команды: find|tableName|columnName|value|column2|value2|...|columnN|valueN\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\t\tcolumn1 - имя первого столбца\n" +
                "\t\tvalue1 - значение первого столбца\n" +
                "\t\tcolumn2 - имя второго столбца\n" +
                "\t\tvalue2 - значение второго столбца\n" +
                "\t\tcolumnN - имя n-го столбца\n" +
                "\t\tvalueN - имя n-го столбца\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "insert\n" +
                "\tКоманда для вставки одной строки в заданную таблицу\n" +
                "\tФормат команды: insert|tableName|column1|value1|column2|value2|...|columnN|valueN\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\tcolumn1 - имя первого столбца записи\n" +
                "\tvalue1 - значение первого столбца записи\n" +
                "\tcolumn2 - имя второго столбца записи\n" +
                "\tvalue2 - значение второго столбца записи\n" +
                "\tcolumnN - имя n-го столбца записи\n" +
                "\tvalueN - имя n-го столбца записи\n" +
                "\t\tNUMBER --- записывается число без ковычек   columnName|123\n" +
                "\t\tVARCHAR2 - строковое значение записывается в ковычках   columnName|'Hello'\n" +
                "\t\tDATE ----- даты записываются следующим образом  to_date('20160321','YYYYMMDD')\n" +
                "\tФормат вывода: текстовое сообщение с результатом выполнения операции\r\n" +
                "update\n" +
                "\tКоманда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1\n" +
                "\tФормат команды: update|tableName|column1|value1|column2|value2\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\tcolumn1 - имя столбца записи которое проверяется\n" +
                "\tvalue1 - значение которому должен соответствовать столбец column1 для обновляемой записи\n" +
                "\tcolumn2 - имя обновляемого столбца записи\n" +
                "\tvalue2 - значение обновляемого столбца записи\n" +
                "\tcolumnN - имя n-го обновляемого столбца записи\n" +
                "\tvalueN - значение n-го обновляемого столбца записи\n" +
                "\t\tNUMBER --- записывается число без ковычек   columnName|123\n" +
                "\t\tVARCHAR2 - строковое значение записывается в ковычках   columnName|'Hello'\n" +
                "\t\tDATE ----- даты записываются следующим образом  to_date('20160321','YYYYMMDD')\n" +
                "\tКоличесьво обновляемых параметров и параметров по которым обновлять должно быть одинаковым!\n" +
                "\tФормат вывода: табличный, как при find со старыми значениями обновленных записей.\r\n" +
                "delete\n" +
                "\tКоманда удаляет одну или несколько записей для которых соблюдается условие column = value\n" +
                "\tФормат команды: delete|tableName|column|value\n" +
                "\tгде: tableName - имя таблицы\n" +
                "\tcolumn - имя столбца записи которое проверяется\n" +
                "\tvalue - значение которому должен соответствовать столбец column1 для удаляемой записи\n" +
                "\tФормат вывода: табличный, как при find со старыми значениями удаляемых записей.\r\n" +
                "readQuery\n" +
                "\tКоманда для ввода SQL запроса\n" +
                "\tФормат команды: readQuery|SQLQuery(Только чтение из БД)\n" +
                "\tгде: SQLQuery - ваш SQL запрос\n" +
                "\tФормат вывода: табличка в консольном формате\r\n" +
                "cudQuery\n" +
                "\tФормат команды: cudQuery|SQLQuery (Для внесения изменений в таблицу)\n" +
                "\tгде: SQLQuery - ваш SQL запрос\n" +
                "\tФормат вывода: текстовое сообщение с результатом выполнения операции\r\n" +
                "history\n" +
                "\tКоманда для получения истории работы с приложением\n" +
                "\tФормат команды: history\n" +
                "\tФормат вывода: дата и время использования команды -> действия\n" +
                "\t\t\t\t\t\t\tрезультат выполнения команды\r\n" +
                "help\n" +
                "\tКоманда выводит в консоль список всех доступных команд\n" +
                "\tФормат команды: help\n" +
                "\tФормат вывода: текст, описания команд\r\n" +
                "exit\n" +
                "\tКоманда для отключения от БД и выход из приложения\n" +
                "\tФормат команды: exit\n" +
                "\tФормат вывода: текстовое сообщение\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "До встречи!\r\n";
       assertEquals(actualResult , getData());
    }

    @Test
    public void test_Exit(){
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                "\tВас приветствует приложение SqlCMD\n" +
                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "До встречи!\r\n";

        assertEquals(actualResult , getData());

    }

    @Test
    public void test_Connect() {

        in.add("connect|test|pass");
        in.add("exit");

        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";

        assertEquals(actualResult , getData());

    }

    @Test
    public void test_ConnectWrongInput(){

        in.add("connect|test|dssdas");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Не удалось подключиться к базе данных ORA-01017: invalid username/password; logon denied\n" +
                        "\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_unsupported(){

        in.add("unsupported");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Вы не можете пользоваться командами, пока не подключитесь с помощью комманды connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_NotExist(){

        in.add("connect|test|pass");
        in.add("kfjllks");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Несуществующая команда: kfjllks\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_IllegalArguments(){

        in.add("connect|test|pass|llls");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Ошибка Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: 4\r\n" +
                        "Вы не можете пользоваться командами, пока не подключитесь с помощью комманды connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";

        assertEquals(actualResult, getData());

    }

    @Test
    public void test_tables(){

        in.add("connect|test|pass");
        in.add("tables");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                                "\tВас приветствует приложение SqlCMD\n" +
                                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "--------------\r\n" +
                                "| ALL_TABLES |\r\n" +
                                "--------------\r\n" +
                                "| TABLE_NAME |\r\n" +
                                "--------------\r\n" +
                                "--------------\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_creteTableWithoutPK(){
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_creteTableWithPK(){
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("y");
        in.add("TEST4");
        in.add("n");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                        "Введите название колонки, которой хотите присвоить ключ \r\n" +
                        "Успех! Первичный ключ создан\r\n" +
                        "Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него.\n" +
                        "Хотите это сделать? (Y/N)\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_creteTableWithPKWithSeq(){
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("y");
        in.add("TEST4");
        in.add("y");
        in.add("1");
        in.add("cudQuery|DROP SEQUENCE FIRST_SEQ");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                                "\tВас приветствует приложение SqlCMD\n" +
                                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица создана\r\n" +
                                "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                                "Введите название колонки, которой хотите присвоить ключ \r\n" +
                                "Успех! Первичный ключ создан\r\n" +
                                "Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него.\n" +
                                "Хотите это сделать? (Y/N)\r\n" +
                                "Введите значени с которого будет начинаться отсчет \r\n" +
                                "Успех! Sequence генератор создан\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Запрос выполнен\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица удалена\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_columnsFindWrongQuery() {
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("columns|FIRST");
        in.add("cudQuery|jdjjidijisj");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "---------------\r\n" +
                        "|    FIRST    |\r\n" +
                        "---------------\r\n" +
                        "| COLUMN_NAME |\r\n" +
                        "---------------\r\n" +
                        "|    TEST     |\r\n" +
                        "|    TEST1    |\r\n" +
                        "|    TEST2    |\r\n" +
                        "|    TEST3    |\r\n" +
                        "|    TEST4    |\r\n" +
                        "---------------\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Ошибка. Не удалось выполнить ваш запрос ( jdjjidijisj ) ORA-00900: invalid SQL statement\n" +
                        "\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());
    }

    @Test
    public void test_tableTypeReadQuery() {
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("tabletype|FIRST");
        in.add("readQuery|SELECT * FROM FIRST");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "-----------------------------------------\r\n" +
                        "|                 FIRST                 |\r\n" +
                        "-----------------------------------------\r\n" +
                        "| COLUMN_NAME |  DATA_TYPE   | NULLABLE |\r\n" +
                        "-----------------------------------------\r\n" +
                        "|    TEST     | VARCHAR2(20) |    N     |\r\n" +
                        "|    TEST1    |  NUMBER(22)  |    Y     |\r\n" +
                        "|    TEST2    | VARCHAR2(10) |    Y     |\r\n" +
                        "|    TEST3    |   DATE(7)    |    Y     |\r\n" +
                        "|    TEST4    |  NUMBER(22)  |    N     |\r\n" +
                        "-----------------------------------------\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "----------------------------------------\r\n" +
                        "|              Your Query              |\r\n" +
                        "----------------------------------------\r\n" +
                        "| TEST | TEST1 | TEST2 | TEST3 | TEST4 |\r\n" +
                        "----------------------------------------\r\n" +
                        "----------------------------------------\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());
    }

    @Test
    public void test_columnTypeInsert() {
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("columntype|FIRST|TEST");
        in.add("insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155");
        in.add("no");
        in.add("find|FIRST");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                                "\tВас приветствует приложение SqlCMD\n" +
                                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица создана\r\n" +
                                "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "-----------------------------------------\r\n" +
                                "|                 FIRST                 |\r\n" +
                                "-----------------------------------------\r\n" +
                                "| COLUMN_NAME |  DATA_TYPE   | NULLABLE |\r\n" +
                                "-----------------------------------------\r\n" +
                                "|    TEST     | VARCHAR2(20) |    N     |\r\n" +
                                "-----------------------------------------\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                "Успех! Данные добавлены\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "|                        FIRST                        |\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "| TEST  | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "| Hello |  12   | World | 1999-03-21 00:00:00 |  155  |\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица удалена\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "До встречи!\r\n";
        assertEquals(actualResult, getData());
    }

    @Test
    public void test_deleteClear() {
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("columntype|FIRST|TEST");
        in.add("insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155");
        in.add("no");
        in.add("insert|FIRST|TEST|'Java'|TEST1|19|TEST2|'JUJA'|TEST3|to_date('19930321','YYYYMMDD')|TEST4|233");
        in.add("no");
        in.add("insert|FIRST|TEST|'C++'|TEST1|2|TEST2|'Other'|TEST3|to_date('19800321','YYYYMMDD')|TEST4|99");
        in.add("no");
        in.add("find|FIRST");
        in.add("delete|FIRST|TEST|'C++'");
        in.add("clear|FIRST");
        in.add("find|FIRST");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\tВас приветствует приложение SqlCMD\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "-----------------------------------------\r\n" +
                        "|                 FIRST                 |\r\n" +
                        "-----------------------------------------\r\n" +
                        "| COLUMN_NAME |  DATA_TYPE   | NULLABLE |\r\n" +
                        "-----------------------------------------\r\n" +
                        "|    TEST     | VARCHAR2(20) |    N     |\r\n" +
                        "-----------------------------------------\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                        "Успех! Данные добавлены\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                        "Успех! Данные добавлены\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                        "Успех! Данные добавлены\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "|                        FIRST                        |\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "| TEST  | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "| Hello |  12   | World | 1999-03-21 00:00:00 |  155  |\r\n" +
                        "| Java  |  19   | JUJA  | 1993-03-21 00:00:00 |  233  |\r\n" +
                        "|  C++  |   2   | Other | 1980-03-21 00:00:00 |  99   |\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "|                        FIRST                        |\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "| TEST  | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "| Hello |  12   | World | 1999-03-21 00:00:00 |  155  |\r\n" +
                        "| Java  |  19   | JUJA  | 1993-03-21 00:00:00 |  233  |\r\n" +
                        "-------------------------------------------------------\r\n" +
                        "Успех! запись была удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица была очищена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "----------------------------------------\r\n" +
                        "|                FIRST                 |\r\n" +
                        "----------------------------------------\r\n" +
                        "| TEST | TEST1 | TEST2 | TEST3 | TEST4 |\r\n" +
                        "----------------------------------------\r\n" +
                        "----------------------------------------\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());
    }

    @Test
    public void test_updateFindSettings() {
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155");
        in.add("no");
        in.add("insert|FIRST|TEST|'Java'|TEST1|19|TEST2|'JUJA'|TEST3|to_date('19930321','YYYYMMDD')|TEST4|233");
        in.add("no");
        in.add("insert|FIRST|TEST|'C++'|TEST1|2|TEST2|'Other'|TEST3|to_date('19800321','YYYYMMDD')|TEST4|99");
        in.add("no");
        in.add("find|FIRST");
        in.add("findsettings|FIRST|TEST|'Java'|TEST4|233");
        in.add("update|FIRST|TEST|'Guitar'|TEST4|500|TEST|'Java'|TEST4|233");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                                "\tВас приветствует приложение SqlCMD\n" +
                                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица создана\r\n" +
                                "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                "Успех! Данные добавлены\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                "Успех! Данные добавлены\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                "Успех! Данные добавлены\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "|                        FIRST                        |\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "| TEST  | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "| Hello |  12   | World | 1999-03-21 00:00:00 |  155  |\r\n" +
                                "| Java  |  19   | JUJA  | 1993-03-21 00:00:00 |  233  |\r\n" +
                                "|  C++  |   2   | Other | 1980-03-21 00:00:00 |  99   |\r\n" +
                                "-------------------------------------------------------\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "------------------------------------------------------\r\n" +
                                "|                       FIRST                        |\r\n" +
                                "------------------------------------------------------\r\n" +
                                "| TEST | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                                "------------------------------------------------------\r\n" +
                                "| Java |  19   | JUJA  | 1993-03-21 00:00:00 |  233  |\r\n" +
                                "------------------------------------------------------\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "--------------------------------------------------------\r\n" +
                                "|                        FIRST                         |\r\n" +
                                "--------------------------------------------------------\r\n" +
                                "|  TEST  | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                                "--------------------------------------------------------\r\n" +
                                "| Hello  |  12   | World | 1999-03-21 00:00:00 |  155  |\r\n" +
                                "| Guitar |  19   | JUJA  | 1993-03-21 00:00:00 |  500  |\r\n" +
                                "|  C++   |   2   | Other | 1980-03-21 00:00:00 |  99   |\r\n" +
                                "--------------------------------------------------------\r\n" +
                                "Успех! Данные обновлены\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица удалена\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "До встречи!\r\n";
        assertEquals(actualResult, getData());
    }

    @Test
    public void test_fileTable() {
        in.add("connect|test|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155");
        in.add("no");
        in.add("insert|FIRST|TEST|'Java'|TEST1|19|TEST2|'JUJA'|TEST3|to_date('19930321','YYYYMMDD')|TEST4|233");
        in.add("no");
        in.add("insert|FIRST|TEST|'C++'|TEST1|2|TEST2|'Other'|TEST3|to_date('19800321','YYYYMMDD')|TEST4|99");
        in.add("no");
        in.add("filetable|FIRST");
        in.add("y");
        in.add("y");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                                        "\tВас приветствует приложение SqlCMD\n" +
                                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "Успех, вы подключились к базе данных: Oracle Database - Production\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "Успех! Таблица создана\r\n" +
                                        "Присвоить колонке первичный ключ? (Y/N)\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                        "Успех! Данные добавлены\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                        "Успех! Данные добавлены\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "Добавить Seq генератор, если такой имеется? Y/N\r\n" +
                                        "Успех! Данные добавлены\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "-------------------------------------------------------\r\n" +
                                        "|                        FIRST                        |\r\n" +
                                        "-------------------------------------------------------\r\n" +
                                        "| TEST  | TEST1 | TEST2 |        TEST3        | TEST4 |\r\n" +
                                        "-------------------------------------------------------\r\n" +
                                        "| Hello |  12   | World | 1999-03-21 00:00:00 |  155  |\r\n" +
                                        "| Java  |  19   | JUJA  | 1993-03-21 00:00:00 |  233  |\r\n" +
                                        "|  C++  |   2   | Other | 1980-03-21 00:00:00 |  99   |\r\n" +
                                        "-------------------------------------------------------\r\n" +
                                        "Сохранить эти данные в файл? Y/N\r\n" +
                                        "Имя файла будет соответствовать имени таблицы. Если согланы, введите Y\r\n" +
                                        "Если хотите переименовать файл, нажмите Enter\r\n" +
                                        "Данные сохранены в файл\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "Успех! Таблица удалена\r\n" +
                                        "Введи команду (или help для помощи):\r\n" +
                                        "До встречи!\r\n";

        File file = new File("FIRST" + ".txt");
        if(file.exists()){
            assertTrue(true);

            if(file.delete()){
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
        assertEquals(actualResult, getData());
    }

    @Test
    public void test_consolePrintHistory(){
        in.add("connect|test|pass");
        in.add("history");
        in.add("exit");

        Main.main(new String[0]);

        assertFalse(getData().equals(""));
    }
}
