package ua.com.juja.sqlcmd.view;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConsoleTest {
    private View view;

    @Before
    public void start(){
        view = new Console();
    }

    @Test
    public void test_printTable(){
        assertEquals("", view.printTable(null));
    }
}
