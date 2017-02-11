package ua.com.juja.sqlcmd.control;

import org.junit.*;

import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Solyk on 26.01.2017.
 */
public class JDBCDatabaseManagerTest {

    private DatabaseManager manager;
    private View view;

    @Before
    public void start(){
            manager = new JDBCDatabaseManager();
            view = new Console();
        }

    @After
    public void purgeRecyclebin() throws SQLException {
        if(manager.isConnected()) {
            manager.cudQuery("PURGE RECYCLEBIN");
        } else {
            // do nothing
        }
    }

    private void connectUserPass() {
        try {
            manager.connect("user", "pass");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dropTable() throws SQLException {
        manager.drop(getTableName());
    }

    private void tryDropTable() {
        try {
            dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropSEQ() throws SQLException {
        manager.cudQuery("DROP SEQUENCE FIRST_SEQ");
    }

    private ArrayList<String> getNewTable1col() {
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        return settings;
    }

    private ArrayList<String> getNewTable2col() {
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        settings.add("TEST1  NUMBER(10) NULL");
        return settings;
    }

    private ArrayList<String> getNewTable3col() {
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        settings.add("TEST1  NUMBER (10) NOT NULL");
        settings.add("TEST2  VARCHAR2 (10 BYTE) NULL");
        return settings;
    }

    private ArrayList<String> getNewTAble5col() {
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        settings.add("TEST1  NUMBER (10) NULL");
        settings.add("TEST2 VARCHAR2(10 BYTE) NULL");
        settings.add("TEST3 DATE NULL");
        settings.add("TEST4 NUMBER(10) NOT NULL");
        return settings;
    }

    private String getTableName(){return "FIRST";}

    private ArrayList<String[]> getInsertFor3Col() {
        ArrayList<String[]> insert = new ArrayList<>();
        insert.add(new String[]{"TEST","'Hello'"});
        insert.add(new String[]{"TEST1","21"});
        insert.add(new String[]{"TEST2","'World'"});
        return insert;
    }

    @Test
    public void connection(){
        connectUserPass();
        Assert.assertTrue(manager.isConnected());
        }

    @Test
    public void connection_null(){
        Assert.assertFalse(manager.isConnected());
    }

    @Test
    public void connection_fail() {
        try {
            manager.connect("ewe", "werer");
        } catch (SQLException e) {
            Assert.assertFalse(manager.isConnected());
        }

    }

    @Test
    public void getAllTableNames() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTable1col();
        manager.createTableWithoutPK(getTableName(), settings);
        Table table = manager.getAllTableNames();
        String result = view.printTable(table);
        String actualResult =
                "--------------\n" +
                "| ALL_TABLES |\n" +
                "--------------\n" +
                "| TABLE_NAME |\n" +
                "--------------\n" +
                "|   FIRST    |\n" +
                "--------------\n";

        dropTable();
        assertEquals(actualResult, result);

    }

    @Test
    public void getAllTableNames_empty() throws SQLException {

        connectUserPass();

        Table table = manager.getAllTableNames();
        String result = view.printTable(table);
        String actualResult =
                "--------------\n" +
                "| ALL_TABLES |\n" +
                "--------------\n" +
                "| TABLE_NAME |\n" +
                "--------------\n" +
                "--------------\n";
        assertEquals(actualResult, result);

    }

    @Test
    public void getAllColumnNamesFromTable_wrongInput() throws SQLException {
        connectUserPass();

        Table  table = manager.getAllColumnNamesFromTable("CCC");
        String result = view.printTable(table);
        String actualResult =
                "---------------\n" +
                "|     CCC     |\n" +
                "---------------\n" +
                "| COLUMN_NAME |\n" +
                "---------------\n" +
                "---------------\n";
        assertEquals(actualResult, result);

    }

    @Test
    public void getAllColumnNamesFromTable() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTable1col();
        manager.createTableWithoutPK(getTableName(), settings);

        Table table = manager.getAllColumnNamesFromTable(getTableName());

        String result = view.printTable(table);
        String actualResult =
                "---------------\n" +
                "|    FIRST    |\n" +
                "---------------\n" +
                "| COLUMN_NAME |\n" +
                "---------------\n" +
                "|    TEST     |\n" +
                "---------------\n";

        dropTable();
        assertEquals(actualResult, result);

    }

    @Test
    public void getDataTypeAllColumnsFromTable() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTAble5col();
        manager.createTableWithoutPK(getTableName(), settings);
        Table table = manager.getDataTypeAllColumnsFromTable(getTableName());
        String result = view.printTable(table);
        String actualResult =
                        "-----------------------------------------\n" +
                        "|                 FIRST                 |\n" +
                        "-----------------------------------------\n" +
                        "| COLUMN_NAME |  DATA_TYPE   | NULLABLE |\n" +
                        "-----------------------------------------\n" +
                        "|    TEST     | VARCHAR2(20) |    N     |\n" +
                        "|    TEST1    |  NUMBER(22)  |    Y     |\n" +
                        "|    TEST2    | VARCHAR2(10) |    Y     |\n" +
                        "|    TEST3    |   DATE(7)    |    Y     |\n" +
                        "|    TEST4    |  NUMBER(22)  |    N     |\n" +
                        "-----------------------------------------\n";
        dropTable();
        assertEquals(actualResult, result);

    }

    @Test
    public void getDataTypeAllColumnsFromTable_wrongInput() throws SQLException {
        connectUserPass();

        Table  table = manager.getDataTypeAllColumnsFromTable("CCC");
        String result = view.printTable(table);
        String actualResult =
                "--------------------------------------\n" +
                "|                CCC                 |\n" +
                "--------------------------------------\n" +
                "| COLUMN_NAME | DATA_TYPE | NULLABLE |\n" +
                "--------------------------------------\n" +
                "--------------------------------------\n";

        assertEquals(actualResult, result);
    }

    @Test
    public void getDataTypeColumnFromTable() throws SQLException {

        connectUserPass();
        ArrayList<String> settings = getNewTAble5col();
        manager.createTableWithoutPK(getTableName(), settings);

        Table table = manager.getDataTypeColumnFromTable(getTableName(), "TEST1");

        String result = view.printTable(table);
        String actualResult =
                        "---------------------------------------\n" +
                        "|                FIRST                |\n" +
                        "---------------------------------------\n" +
                        "| COLUMN_NAME | DATA_TYPE  | NULLABLE |\n" +
                        "---------------------------------------\n" +
                        "|    TEST1    | NUMBER(22) |    Y     |\n" +
                        "---------------------------------------\n";

        dropTable();
        assertEquals(actualResult, result);
    }

    @Test
    public void getDataTypeColumnFromTable_wrongInput() throws SQLException {
        connectUserPass();

        Table table = manager.getDataTypeColumnFromTable("CCC", "TAT");

        String result = view.printTable(table);
        String actualResult =
                "--------------------------------------\n" +
                "|                CCC                 |\n" +
                "--------------------------------------\n" +
                "| COLUMN_NAME | DATA_TYPE | NULLABLE |\n" +
                "--------------------------------------\n" +
                "--------------------------------------\n";

        assertEquals(actualResult, result);
    }

    @Test
    public void createTableWithoutPK() {
        connectUserPass();
        try {
            ArrayList<String> settings = getNewTable1col();
            manager.createTableWithoutPK(getTableName(), settings);
            dropTable();
            assertTrue(true);
        } catch (SQLException e) {
            tryDropTable();
            assertTrue(false);
        }
    }

    @Test
    public void createTableWithoutPK_wrongInput() {
        connectUserPass();
        try {
            manager.createTableWithoutPK("hs69", new ArrayList<String>());
            assertTrue(false);
        } catch (SQLException e) {
            assertTrue(true);
        }
    }

    @Test
    public void createTableCreatePK() {
        connectUserPass();
        try {
            ArrayList<String> settings = getNewTable2col();
            manager.createTableWithoutPK(getTableName(), settings);
            manager.createTableCreatePK(getTableName(), "TEST1");
            dropTable();
            assertTrue(true);
        } catch (SQLException e) {
            tryDropTable();
            assertTrue(false);
        }
    }

    @Test
    public void createTableCreatePK_wrongInput() {
        connectUserPass();
        try {
            ArrayList<String> settings = getNewTable2col();
            manager.createTableWithoutPK(getTableName(), settings);
            manager.createTableCreatePK(getTableName(), "TEST23");
            dropTable();
            assertTrue(false);
        } catch (SQLException e) {
            tryDropTable();
            assertTrue(true);
        }
    }

    @Test
    public void createTableSequenceForPK() {
        connectUserPass();
        try {
            ArrayList<String> settings = getNewTable3col();
            manager.createTableWithoutPK(getTableName(), settings);
            manager.createTableCreatePK(getTableName(), "TEST1");
            manager.createTableSequenceForPK(getTableName(), new Long(1));
            dropTable();
            dropSEQ();
            assertTrue(true);
        } catch (SQLException e) {
            assertTrue(false);
        }
    }

    @Test
    public void createTableSequenceForPK_wrongInput() {
        connectUserPass();
        try {
            ArrayList<String> settings = getNewTable3col();
            manager.createTableWithoutPK(getTableName(), settings);
            manager.createTableCreatePK(getTableName(), "TEST2");
            manager.createTableSequenceForPK(getTableName(), null);
            dropTable();
            dropSEQ();
            assertTrue(false);
        } catch (SQLException e) {
            tryDropTable();
            assertTrue(true);
        }
    }

    @Test
    public void connection_disconnect(){
        connectUserPass();
        manager.disconnect();
        assertFalse(manager.isConnected());
    }

    @Test
    public void clear() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTable3col();
        ArrayList<String[]> insert = getInsertFor3Col();
        manager.createTableWithoutPK(getTableName(), settings);
        manager.insert(getTableName(), insert, false);
        manager.clear(getTableName());
        Table table = manager.readTable(getTableName());
        String result = view.printTable(table);
        String actualResult =
                "------------------------\n" +
                "|        FIRST         |\n" +
                "------------------------\n" +
                "| TEST | TEST1 | TEST2 |\n" +
                "------------------------\n" +
                "------------------------\n";
        dropTable();
        assertEquals(actualResult, result);
    }

    @Test
    public void clear_PK() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTable3col();
        ArrayList<String[]> insert = getInsertFor3Col();
        manager.createTableWithoutPK(getTableName(), settings);
        manager.createTableCreatePK(getTableName(), "TEST1");
        manager.createTableSequenceForPK(getTableName(), new Long(2));
        manager.insert(getTableName(), insert, true);
        manager.clear(getTableName());
        Table table = manager.readTable(getTableName());
        String result = view.printTable(table);
        String actualResult =
                        "------------------------\n" +
                        "|        FIRST         |\n" +
                        "------------------------\n" +
                        "| TEST | TEST1 | TEST2 |\n" +
                        "------------------------\n" +
                        "------------------------\n";
        dropSEQ();
        dropTable();
        assertEquals(actualResult, result);
    }

    @Test
    public void drop() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTable3col();
        manager.createTableWithoutPK(getTableName(), settings);
        manager.createTableWithoutPK("SECOND", settings);
        manager.drop("SECOND");
        Table table = manager.getAllTableNames();
        String result = view.printTable(table);
        String actualResult =
                "--------------\n" +
                "| ALL_TABLES |\n" +
                "--------------\n" +
                "| TABLE_NAME |\n" +
                "--------------\n" +
                "|   FIRST    |\n" +
                "--------------\n";
        dropTable();
        assertEquals(actualResult, result);
    }

    @Test
    public void drop_wrongImport() throws SQLException {
        connectUserPass();
        ArrayList<String> settings = getNewTable3col();
        manager.createTableWithoutPK(getTableName(), settings);
        manager.createTableWithoutPK("SECOND", settings);
        try {
            manager.drop("Gdhd");
        } catch (SQLException e){
            manager.drop("SECOND");
            dropTable();
            Assert.assertTrue(true);
        }
    }


}
