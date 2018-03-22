package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SettingsHelperTest {

    private SettingsHelper settingsHelper;
    private Correctly correctly;
    private final static int PARAMETERS_COUNT = 4;

    @Before
    public void setUp(){
        settingsHelper = new SettingsHelper();
        correctly = new Correctly();
    }

    @Test
    public void test_addSettingsLastColumn(){
        String[] data = correctly.expectedMinEven(
                "insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155",
                PARAMETERS_COUNT);
        List<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST4", ""});
        List<String[]> result = settingsHelper.addSettings(data, settings);
        StringBuilder resultEq = new StringBuilder();
        for (String[] aResult : result) {
            resultEq.append(aResult[0]).append(" ").append(aResult[1]).append(", ");
        }

        assertEquals("TEST4 , TEST 'Hello', TEST1 12, TEST2 'World', TEST3 to_date('19990321','YYYYMMDD'), ", resultEq.toString());
    }

    @Test
    public void test_addSettingsFirstColumn(){
        String[] data = correctly.expectedMinEven(
                "insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155",
                PARAMETERS_COUNT);
        List<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", ""});
        List<String[]> result = settingsHelper.addSettings(data, settings);
        StringBuilder resultEq = new StringBuilder();
        for (String[] aResult : result) {
            resultEq.append(aResult[0]).append(" ").append(aResult[1]).append(", ");
        }

        assertEquals("TEST , TEST1 12, TEST2 'World', TEST3 to_date('19990321','YYYYMMDD'), TEST4 155, ", resultEq.toString());
    }

    @Test
    public void test_addSettingsPreLastColumn(){
        String[] data = correctly.expectedMinEven(
                "insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155",
                PARAMETERS_COUNT);
        List<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST3", ""});
        List<String[]> result = settingsHelper.addSettings(data, settings);
        StringBuilder resultEq = new StringBuilder();
        for (String[] aResult : result) {
            resultEq.append(aResult[0]).append(" ").append(aResult[1]).append(", ");
        }

        assertEquals("TEST3 , TEST 'Hello', TEST1 12, TEST2 'World', TEST4 155, ", resultEq.toString());
    }
}