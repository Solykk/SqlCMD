package ua.com.juja.sqlcmd.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.Main;
import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;



public class IntegrationTest {

    private DatabaseManager databaseManager;
    private ByteArrayOutputStream out;
    private ConfigurableInputStream in;

    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @After
    public void end() {

    }

    public String getData() {
        String result = null;
        try {
            result = new String(out.toByteArray(),"UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }

    }

    @Test
    public void testHelp() {
        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult = "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
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
                "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "До встречи!\r\n";

        assertEquals(actualResult , getData());

    }

    @Test
    public void test_Connect() {

        in.add("connect|user|pass");
        in.add("exit");

        Main.main(new String[0]);
        String actualResult =
                        "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "\t\t\t\t\tУспех, вы подключились к базе данных:\r\n" +
                        "Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";

        assertEquals(actualResult , getData());

    }

    @Test
    public void test_ConnectWrongInput(){

        in.add("connect|user|dssdas");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
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
                "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Вы не можете пользоваться командами, пока не подключитесь с помощью комманды connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_NotExist(){

        in.add("connect|user|pass");
        in.add("kfjllks");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "\t\t\t\t\tУспех, вы подключились к базе данных:\r\n" +
                        "Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Несуществующая команда: kfjllks\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_IllegalArguments(){

        in.add("connect|user|pass|llls");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
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

        in.add("connect|user|pass");
        in.add("tables");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                                "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "\t\t\t\t\tУспех, вы подключились к базе данных:\r\n" +
                                "Oracle Database - Production\r\n" +
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
        in.add("connect|user|pass");
        in.add("create|" + createTable());
        in.add("n");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "\t\t\t\t\tУспех, вы подключились к базе данных:\r\n" +
                        "Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ, если такой имеется? Если да, введите y\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    private String createTable(){
        String result = "FIRST|TEST VARCHAR2(20 BYTE) NOT NULL|TEST1  NUMBER (10) NULL|" +
                "TEST2 VARCHAR2(10 BYTE) NULL|TEST3 DATE NULL|TEST4 NUMBER(10) NOT NULL";
        return result;
    }

    @Test
    public void test_creteTableWithPK(){
        in.add("connect|user|pass");
        in.add("create|" + createTable());
        in.add("y");
        in.add("TEST4");
        in.add("n");
        in.add("drop|FIRST");
        in.add("exit");
        Main.main(new String[0]);
        String actualResult =
                        "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                        "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "\t\t\t\t\tУспех, вы подключились к базе данных:\r\n" +
                        "Oracle Database - Production\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица создана\r\n" +
                        "Присвоить колонке первичный ключ, если такой имеется? Если да, введите y\r\n" +
                        "Введите название колонки, которой хотите присвоить ключ \r\n" +
                        "Успех! Первичный ключ создан\r\n" +
                        "Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него. Хотите это сделать? \r\n" +
                        "Если да, введите y\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "Успех! Таблица удалена\r\n" +
                        "Введи команду (или help для помощи):\r\n" +
                        "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }

    @Test
    public void test_creteTableWithPKWithSeq(){
        in.add("connect|user|pass");
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
                                "\t\t\t\t\tВас приветствует приложение SqlCMD\r\n" +
                                "Пожалуйста, введите данные для подключения к базе данных в формате: connect|username|password\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "\t\t\t\t\tУспех, вы подключились к базе данных:\r\n" +
                                "Oracle Database - Production\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица создана\r\n" +
                                "Присвоить колонке первичный ключ, если такой имеется? Если да, введите y\r\n" +
                                "Введите название колонки, которой хотите присвоить ключ \r\n" +
                                "Успех! Первичный ключ создан\r\n" +
                                "Если ваш первичный ключ - числовое значение, можно создать Sequence генератор для него. Хотите это сделать? \r\n" +
                                "Если да, введите y\r\n" +
                                "Введите значени с которого будет начинаться отсчет \r\n" +
                                "Успех! Первичный ключ создан\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Запрос выполнен\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "Успех! Таблица удалена\r\n" +
                                "Введи команду (или help для помощи):\r\n" +
                                "До встречи!\r\n";
        assertEquals(actualResult, getData());

    }


}
