package ua.com.juja.sqlcmd.control;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void connection() throws SQLException {

                manager.connect("user", "pass");

            Assert.assertTrue(manager.isConnected());
        }

    @Test
    public void connection_null(){
        Assert.assertFalse(manager.isConnected());
    }

    @Test
    public void fail_connection() {

        try {
            manager.connect("ewe", "werer");
        } catch (SQLException e) {

        }
        Assert.assertFalse(manager.isConnected());
        }

    @Test
    public void getAllTableNames() throws SQLException {
        manager.connect("user", "pass");
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        manager.createTableWithoutPK("FIRST", settings);
        Table table = manager.getAllTableNames();
        String result = view.printTable(table);
        String actualResult = "--------------\n" +
                "| ALL_TABLES |\n" +
                "--------------\n" +
                "| TABLE_NAME |\n" +
                "--------------\n" +
                "|   FIRST    |\n" +
                "--------------\n";
        manager.drop("FIRST");

        assertEquals(actualResult, result);

    }

    @Test
    public void getAllTableNames_empty() throws SQLException {

        manager.connect("user", "pass");
        Table table = null;
        table = manager.getAllTableNames();
        String result = view.printTable(table);
        assertEquals(
                        "--------------\n" +
                        "| ALL_TABLES |\n" +
                        "--------------\n" +
                        "| TABLE_NAME |\n" +
                        "--------------\n" +
                        "--------------\n", result);

    }

    @Test
    public void createTable_actualInput() {
        try {
            manager.connect("user", "pass");
        } catch (SQLException e) {

        }
           try {
               ArrayList<String> settings = new ArrayList<String>();
               settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
               manager.createTableWithoutPK("FIRST", settings);
               manager.drop("FIRST");
               assertTrue(true);
           } catch (SQLException e) {
           assertTrue(false);
        }
    }

    @Test
    public void createTable_failInput() {
        try {
            manager.connect("user", "pass");
        } catch (SQLException e) {

        }
           try {
           manager.createTableWithoutPK("hs69", new ArrayList<String>());
               assertTrue(false);
           } catch (SQLException e) {
               assertTrue(true);
        }
    }

    @Test
    public void getAllColumnNamesFromTable_wrongInput() throws SQLException {
        try {
            manager.connect("user", "pass");
        } catch (SQLException e) {

        }

        Table  table = manager.getAllColumnNamesFromTable("CCC");
        String result = view.printTable(table);
        assertEquals(
                    "---------------\n" +
                    "|     CCC     |\n" +
                    "---------------\n" +
                    "| COLUMN_NAME |\n" +
                    "---------------\n" +
                    "---------------\n",result);

    }

    @Test
    public void getAllColumnNamesFromTable() throws SQLException {
        manager.connect("user", "pass");
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        manager.createTableWithoutPK("FIRST", settings);
        Table table = manager.getAllColumnNamesFromTable("FIRST");
        String result = view.printTable(table);
        String actualResult =
                "---------------\n" +
                "|    FIRST    |\n" +
                "---------------\n" +
                "| COLUMN_NAME |\n" +
                "---------------\n" +
                "|    TEST     |\n" +
                "---------------\n";
        manager.drop("FIRST");

        assertEquals(actualResult, result);

    }

    @Test
    public void getDataTypeAllColumnsFromTable() throws SQLException {
        manager.connect("user", "pass");
        ArrayList<String> settings = new ArrayList<String>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        settings.add("TEST1  NUMBER (10) NULL");
        settings.add("TEST2 VARCHAR2(10 BYTE) NULL");
        settings.add("TEST3 DATE NULL");
        settings.add("TEST4 NUMBER(10) NOT NULL");
        manager.createTableWithoutPK("FIRST", settings);
        Table table = manager.getDataTypeAllColumnsFromTable("FIRST");
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
        manager.drop("FIRST");
        assertEquals(actualResult, result);

    }

    @Test
    public void getDataTypeAllColumnsFromTable_wrongInput() throws SQLException {
        try {
            manager.connect("user", "pass");
        } catch (SQLException e) {

        }

        Table  table = manager.getDataTypeAllColumnsFromTable("CCC");
        String result = view.printTable(table);

        assertEquals(
                        "--------------------------------------\n" +
                        "|                CCC                 |\n" +
                        "--------------------------------------\n" +
                        "| COLUMN_NAME | DATA_TYPE | NULLABLE |\n" +
                        "--------------------------------------\n" +
                        "--------------------------------------\n",result);
    }

}
