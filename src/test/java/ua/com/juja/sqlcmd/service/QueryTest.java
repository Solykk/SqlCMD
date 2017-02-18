package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class QueryTest {

    private Query query;

    @Before
    public void start(){
        query = new Query();
    }

    @Test
    public void test_insertQueryTrue(){

        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", " "});
        settings.add(new String[]{"TEST1", "35"});
        settings.add(new String[]{"TEST2", "World"});

        assertEquals("INSERT INTO FIRST( TEST, TEST1, TEST2) VALUES ( FIRST_SEQ.nextval, 35, World )",
                    query.insertQuery("FIRST", settings, true));

    }

    @Test
    public void test_insertQueryFalse(){

        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", "'Hello'"});
        settings.add(new String[]{"TEST1", "35"});
        settings.add(new String[]{"TEST2", "World"});

        assertEquals("INSERT INTO FIRST( TEST, TEST1, TEST2) VALUES ( 'Hello', 35, World )",
                query.insertQuery("FIRST", settings, false));

    }

    @Test
    public void test_createSPKQuery(){
        assertEquals("CREATE SEQUENCE FIRST_seq START WITH 1",
                query.createSPKQuery("FIRST", new Long(1)));

    }

    @Test
    public void test_createPKQuery(){
        assertEquals("ALTER TABLE FIRST ADD (CONSTRAINT FIRST_PK PRIMARY KEY (TEST))",
                query.createPKQuery("FIRST", "TEST"));

    }

    @Test
    public void test_createWPKQuery(){
        ArrayList<String> settings = new ArrayList<>();
        settings.add("TEST VARCHAR2(20 BYTE) NOT NULL");
        settings.add("TEST1  NUMBER (10) NOT NULL");
        settings.add("TEST2  VARCHAR2 (10 BYTE) NULL");

        assertEquals("CREATE TABLE FIRST (TEST VARCHAR2(20 BYTE) NOT NULL, " +
                "TEST1  NUMBER (10) NOT NULL, TEST2  VARCHAR2 (10 BYTE) NULL )",
                query.createWPKQuery("FIRST", settings));
    }

    @Test
    public void test_updateQuery(){
        ArrayList<String[]> forUpdate = new ArrayList<>();
        forUpdate.add(new String[]{"TEST", "'Hello'"});
        forUpdate.add(new String[]{"TEST", "'World'"});

        ArrayList<String[]> howUpdate = new ArrayList<>();
        howUpdate.add(new String[]{"TEST", "'Good'"});
        howUpdate.add(new String[]{"TEST", "'Luck'"});

        assertEquals("UPDATE FIRST SET TEST = 'Good', TEST = 'Luck' WHERE TEST = 'Hello' AND TEST = 'World'",
                query.updateQuery("FIRST", howUpdate, forUpdate));
    }

    @Test
    public void test_deleteQuery(){
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", "'Hello'"});
        settings.add(new String[]{"TEST", "'World'"});

        assertEquals("DELETE FROM FIRST WHERE TEST = 'Hello' AND TEST = 'World'",
                query.deleteQuery("FIRST", settings));
    }

    @Test
    public void test_readSetQuery(){
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", "'Hello'"});
        settings.add(new String[]{"TEST", "'World'"});

        assertEquals("SELECT * FROM FIRST WHERE TEST = 'Hello' AND TEST = 'World'",
                query.readSetQuery("FIRST", settings));
    }

    @Test
    public void test_getTypCloQuery(){

        assertEquals("SELECT COLUMN_NAME , data_type, DATA_LENGTH, NULLABLE " +
                "FROM all_tab_columns WHERE TABLE_NAME = 'FIRST' AND COLUMN_NAME = 'TEST'",
                query.getTypCloQuery("FIRST", "TEST"));
    }

    @Test
    public void test_getAllTypCloQuery(){

        assertEquals("SELECT COLUMN_NAME , DATA_TYPE, DATA_LENGTH, NULLABLE FROM " +
                "ALL_TAB_COLUMNS WHERE TABLE_NAME = 'FIRST'",
                query.getAllTypCloQuery("FIRST"));
    }

    @Test
    public void test_tableNQuery(){

        assertEquals("SELECT TABLE_NAME FROM user_tables",
                query.tableNQuery());
    }

    @Test
    public void test_getColNQuery(){

        assertEquals("SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'FIRST'",
                query.getColNQuery("FIRST"));
    }

    @Test
    public void test_selectAll(){

        assertEquals("SELECT * FROM FIRST",
                query.selectAll("FIRST"));
    }


}
