package ua.com.juja.sqlcmd.control.commands;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.ViewImpl;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CommandsTest {

    private Clear clear;
    private Columns columns;
    private ColumnType columnType;
    private Delete delete;
    private Drop drop;
    private ViewImpl viewImpl;
    private DatabaseManager manager;
    private Find find;
    private FindSettings findSettings;
    private History history;
    private Insert insert;
    private ReadQuery readQuery;
    private Tables tables;
    private TableType tableType;
    private Update update;
    private Create create;

    @Before
    public void start() throws SQLException {
        manager = new JDBCDatabaseManager();
        viewImpl = new ViewImpl();
        clear = new Clear(manager, viewImpl);
        columns = new Columns(manager, viewImpl);
        columnType = new ColumnType(manager, viewImpl);
        delete = new Delete(manager, viewImpl);
        drop = new Drop(manager, viewImpl);
        find = new Find(manager, viewImpl);
        findSettings = new FindSettings(manager, viewImpl);
        history = new History(viewImpl);
        insert = new Insert(manager, viewImpl);
        readQuery = new ReadQuery(manager, viewImpl);
        tables = new Tables(manager, viewImpl);
        tableType = new TableType(manager, viewImpl);
        update = new Update(manager, viewImpl);
        create = new Create(manager, viewImpl);


        manager.connect("user","pass");

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
    public void test_tablesQueryProcessFail(){
        manager.disconnect();
        tables.process("tables");

        assertEquals("Ошибка. Не могу осуществить вывод всех таблиц null\n",
                viewImpl.getOut());
    }

    @Test
    public void test_tableTypeQueryProcessFail(){
        manager.disconnect();
        tableType.process("tabletype|FIRST");

        assertEquals("Ошибка. Не удалось определить тип данных колонок в таблице ( FIRST ) null\n",
                viewImpl.getOut());
    }

    @Test
    public void test_updateTypeQueryProcessFail(){
        update.process("update|FIRST|TEST|'Hello'|TEST1|'Bay'");

        assertEquals("Ошибка. Не удалось обновить таблицу ( FIRST ) ORA-00942: table or view does not exist\n\n",
                viewImpl.getOut());
    }

    @Test
    public void test_createTypeQueryProcessFail(){
        create.process("create|FIRST|TEST|'Hello'|TEST1|'Bay'");

        assertEquals("Ошибка. Не удалось создать таблицу ( FIRST ) ORA-00904: : invalid identifier\n\n",
                viewImpl.getOut());
    }
}
