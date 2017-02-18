package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.view.View;

import static junit.framework.TestCase.assertEquals;

public class TablePrinterTest {

    private View view;
    private TablePrinter tablePrinter;

    @Before
    public void start(){
        tablePrinter = new TablePrinter(view);
    }

    @Test
    public void test_printTable(){
        assertEquals("", tablePrinter.printTable(null));
    }
}
