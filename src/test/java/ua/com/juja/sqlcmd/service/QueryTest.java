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
        String tableName = "FIRST";
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", " "});
        settings.add(new String[]{"TEST1", "35"});
        settings.add(new String[]{"TEST2", "World"});

        assertEquals("INSERT INTO FIRST( TEST, TEST1, TEST2) VALUES ( FIRST_SEQ.nextval, 35, World )",
                    query.insertQuery(tableName, settings, true));

    }

    @Test
    public void test_insertQueryFalse(){
        String tableName = "FIRST";
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", "'Hello'"});
        settings.add(new String[]{"TEST1", "35"});
        settings.add(new String[]{"TEST2", "World"});

        assertEquals("INSERT INTO FIRST( TEST, TEST1, TEST2) VALUES ( 'Hello', 35, World )",
                query.insertQuery(tableName, settings, false));

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

        assertEquals("CREATE TABLE FIRST (TEST VARCHAR2(20 BYTE) NOT NULL, TEST1  NUMBER (10) NOT NULL, TEST2  VARCHAR2 (10 BYTE) NULL )",
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

}
