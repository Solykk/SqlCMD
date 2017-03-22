package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class SettingsHelperTest {

    private SettingsHelper settingsHelper;
    private Correctly correctly;
    private int parametersCount = 4;

    @Before
    public void setUp(){
        settingsHelper = new SettingsHelper();
        correctly = new Correctly();
    }

    @Test
    public void test_addSettingsLastColumn(){
        String[] data = correctly.expectedMinEven(
                "insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155",
                parametersCount);
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST4", ""});
        ArrayList<String[]> result = settingsHelper.addSettings(data, settings);
        String resultEq = "";
        for (int i = 0; i < result.size(); i++) {
            resultEq += result.get(i)[0] + " " + result.get(i)[1] + ", ";
        }

        assertEquals("TEST4 , TEST 'Hello', TEST1 12, TEST2 'World', TEST3 to_date('19990321','YYYYMMDD'), ", resultEq);
    }

    @Test
    public void test_addSettingsFirstColumn(){
        String[] data = correctly.expectedMinEven(
                "insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155",
                parametersCount);
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST", ""});
        ArrayList<String[]> result = settingsHelper.addSettings(data, settings);
        String resultEq = "";
        for (int i = 0; i < result.size(); i++) {
            resultEq += result.get(i)[0] + " " + result.get(i)[1] + ", ";
        }

        assertEquals("TEST , TEST1 12, TEST2 'World', TEST3 to_date('19990321','YYYYMMDD'), TEST4 155, ", resultEq);
    }

    @Test
    public void test_addSettingsPreLastColumn(){
        String[] data = correctly.expectedMinEven(
                "insert|FIRST|TEST|'Hello'|TEST1|12|TEST2|'World'|TEST3|to_date('19990321','YYYYMMDD')|TEST4|155",
                parametersCount);
        ArrayList<String[]> settings = new ArrayList<>();
        settings.add(new String[]{"TEST3", ""});
        ArrayList<String[]> result = settingsHelper.addSettings(data, settings);
        String resultEq = "";
        for (int i = 0; i < result.size(); i++) {
            resultEq += result.get(i)[0] + " " + result.get(i)[1] + ", ";
        }

        assertEquals("TEST3 , TEST 'Hello', TEST1 12, TEST2 'World', TEST4 155, ", resultEq);
    }

}