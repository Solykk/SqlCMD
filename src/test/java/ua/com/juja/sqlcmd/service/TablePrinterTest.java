package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.view.Console;

import static junit.framework.TestCase.assertEquals;

public class TablePrinterTest {

    private TablePrinter tablePrinter;

    @Before
    public void start(){
        tablePrinter = new TablePrinter();
        tablePrinter.setView(new Console());
    }

    @Test
    public void test_printTableNull(){
        assertEquals("", tablePrinter.printTable(null));
    }
}
