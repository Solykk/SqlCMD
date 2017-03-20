package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.model.ColumnData;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.Console;

import java.util.ArrayList;

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

    @Test
    public void test_printTableBigQuery(){
        ArrayList<String> first = new ArrayList<>();
        first.add("value");
        ColumnData one = new ColumnData("COLUMN_NAME", first);
        ColumnData two = new ColumnData("CO", first);
        ColumnData three = new ColumnData("TE", first);
        ArrayList<ColumnData> columnDatas = new ArrayList<>();
        columnDatas.add(one);
        columnDatas.add(two);
        columnDatas.add(three);

        assertEquals(
                "-------------------------------\n" +
                        "|       BIG_TABLE_NAME        |\n" +
                        "-------------------------------\n" +
                        "| COLUMN_NAME |  CO   |  TE   |\n" +
                        "-------------------------------\n" +
                        "|    value    | value | value |\n" +
                        "-------------------------------\n", tablePrinter.printTable(
                        new Table("BIG_TABLE_NAME",
                                columnDatas)));
    }

    @Test
    public void test_printTableBigQueryOneColumn(){
        ArrayList<String> first = new ArrayList<>();
        first.add("value");
        ColumnData one = new ColumnData("COLUMN_NAME", first);
        ArrayList<ColumnData> columnDatas = new ArrayList<>();
        columnDatas.add(one);

        assertEquals(
                "------------------\n" +
                         "| BIG_TABLE_NAME |\n" +
                         "------------------\n" +
                         "|  COLUMN_NAME   |\n" +
                         "------------------\n" +
                         "|     value      |\n" +
                         "------------------\n", tablePrinter.printTable(
                        new Table("BIG_TABLE_NAME",
                                columnDatas)));
    }

    @Test
    public void test_printTableLittleTableNameBigColumnName(){
        ArrayList<String> first = new ArrayList<>();
        first.add("value");
        ColumnData one = new ColumnData("COLUMN_NAME", first);
        ArrayList<ColumnData> columnDatas = new ArrayList<>();
        columnDatas.add(one);

        assertEquals(
                "---------------\n" +
                         "|    NAME     |\n" +
                         "---------------\n" +
                         "| COLUMN_NAME |\n" +
                         "---------------\n" +
                         "|    value    |\n" +
                         "---------------\n", tablePrinter.printTable(
                        new Table("NAME",
                                columnDatas)));
    }

    @Test
    public void test_printTableLittleTableNameLittleColumnNameBigValue(){
        ArrayList<String> first = new ArrayList<>();
        first.add("BigBigBigValue");
        ColumnData one = new ColumnData("COLUMN", first);
        ArrayList<ColumnData> columnDatas = new ArrayList<>();
        columnDatas.add(one);

        assertEquals(
                "------------------\n" +
                         "|     TABLE      |\n" +
                         "------------------\n" +
                         "|     COLUMN     |\n" +
                         "------------------\n" +
                         "| BigBigBigValue |\n" +
                         "------------------\n", tablePrinter.printTable(
                        new Table("TABLE",
                                columnDatas)));
    }
}
