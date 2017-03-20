package ua.com.juja.sqlcmd.control.commands;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.*;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CommandsTest {

    private DatabaseManager manager;

    private Clear clear;
    private Columns columns;
    private ColumnType columnType;
    private Delete delete;
    private Drop drop;
    private ViewImpl viewImpl;
    private Find find;
    private FindSettings findSettings;
    private History history;
    private Insert insert;
    private ReadQuery readQuery;
    private Tables tables;
    private TableType tableType;
    private Update update;
    private Create create;

    private Services services = new Services();


    @BeforeClass
    public static void dropAll(){
        DropAllHelper.dropAll();
    }

    @AfterClass
    public static  void purge() {
        DropAllHelper.purgeRecycle();
    }

    @Before
    public void start() throws SQLException {
        manager = new JDBCDatabaseManager();
        viewImpl = new ViewImpl();
        services.setView(viewImpl);

        clear = new Clear(manager, services);
        columns = new Columns(manager, services);
        columnType = new ColumnType(manager, services);
        delete = new Delete(manager, services);
        drop = new Drop(manager, services);
        find = new Find(manager, services);
        findSettings = new FindSettings(manager, services);
        history = new History(viewImpl);
        insert = new Insert(manager, viewImpl, services);
        readQuery = new ReadQuery(manager, services);
        tables = new Tables(manager, services);
        tableType = new TableType(manager, services);
        update = new Update(manager, services);
        create = new Create(manager, viewImpl, services);

        manager.connect("test","pass");

    }

    @Test
    public void test_clearProcessFail(){
        clear.process("clear|FIRST");

        assertEquals("Ошибка. Не удалось очистить таблицу ( FIRST ) ORA-00942: table or view does not exist\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_columnsProcessFail(){
        manager.disconnect();
        columns.process("columns|FIRST");

        assertEquals("Ошибка. Не могу осуществить вывод всех колонок таблицы ( FIRST ) null\n",
                viewImpl.getOut());
    }

    @Test
    public void test_columnsTypeProcessFail(){
        manager.disconnect();
        columnType.process("columnType|FIRST|TEST");

        assertEquals("Ошибка. Не удалось определить тип данных колоноки ( TEST ) в таблице ( FIRST ) null\n",
                viewImpl.getOut());
    }

    @Test
    public void test_deleteProcessFail(){
        delete.process("delete|FIRST|TEST|567");

        assertEquals("Ошибка. Не удалось удалить запись в таблице ( FIRST ) ORA-00942: table or view does not exist\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_dropProcessFail(){
        drop.process("drop|FIRST");

        assertEquals("Ошибка. Не удалось удалить таблицу: ( FIRST ) ORA-00942: table or view does not exist\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_findProcessFail(){
        find.process("find|FIRST");

        assertEquals("Ошибка. Не удалось вывести таблицу ( FIRST ) ORA-00942: table or view does not exist\n",
                viewImpl.getOut());
    }

    @Test
    public void test_findSettingsProcessFail(){
        findSettings.process("findsettings|FIRST|TEST|'Hello'");

        assertEquals("Ошибка. Не удалось по критериям вывести таблицу ( FIRST ) ORA-00942: table or view does not exist\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_historyProcessFail(){
        find.process("find|FIRST");
        findSettings.process("findsettings|FIRST|TEST|'Hello'");
        history.process("columnType|FIRST|TEST|'Hello'");

        assertFalse(viewImpl.getHistory().equals(""));
    }

    @Test
    public void test_insertProcessFail(){
        insert.process("insert|FIRST|TEST|'Hello'|TEST|'Bay'");

        assertEquals("Добавить Seq генератор, если такой имеется? Y/N\n" +
                        "Ошибка. Не удалось добавить данные в  таблицу ( FIRST ) ORA-00957: duplicate column name\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_readQueryProcessFail(){
        readQuery.process("readQuery|SELECT , kODJF");

        assertEquals("Ошибка. Не удалось выполнить ваш запрос ( SELECT , kODJF ) ORA-00936: missing expression\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_tablesProcessFail(){
        manager.disconnect();
        tables.process("tables");

        assertEquals("Ошибка. Не могу осуществить вывод всех таблиц null\n",
                viewImpl.getOut());
    }

    @Test
    public void test_tableTypeProcessFail(){
        manager.disconnect();
        tableType.process("tabletype|FIRST");

        assertEquals("Ошибка. Не удалось определить тип данных колонок в таблице ( FIRST ) null\n",
                viewImpl.getOut());
    }

    @Test
    public void test_updateProcessFail(){
        update.process("update|FIRST|TEST|'Hello'|TEST1|'Bay'");

        assertEquals("Ошибка. Не удалось обновить таблицу ( FIRST ) ORA-00942: table or view does not exist\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_createProcessFail(){
        create.process("create|FIRST|TEST|'Hello'|TEST1|'Bay'");

        assertEquals("Ошибка. Не удалось создать таблицу ( FIRST ) ORA-00904: : invalid identifier\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_createWithProcessFail(){
        viewImpl.setCounter(1);
        create.process("create|FIRST|TEST VARCHAR2(20 BYTE) NOT NULL|TEST1  NUMBER (10) NULL|" +
                "TEST2 VARCHAR2(10 BYTE) NULL|TEST3 DATE NULL|TEST4 NUMBER(10) NOT NULL");
        try {
            manager.drop("FIRST");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals(
                        "Успех! Таблица создана\n" +
                        "Присвоить колонке первичный ключ? (Y/N)\n" +
                        "Введите название колонки, которой хотите присвоить ключ \n" +
                        "Ошибка. Не удалось создать первичный ключ ( FIRST ) ORA-00904: \"Y\": invalid identifier\n\n",
                viewImpl.getOut());
    }

}
