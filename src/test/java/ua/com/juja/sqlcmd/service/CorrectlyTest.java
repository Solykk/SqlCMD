package ua.com.juja.sqlcmd.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CorrectlyTest {

    private Correctly correctly;

    @Before
    public void start(){
        correctly = new Correctly();
    }

    @Test
    public void test_expectedTwo(){
        assertEquals("FIRST", correctly.expectedTwo("find|FIRST"));
    }

    @Test
    public void test_expectedTwoWrongInput(){
        try {
            correctly.expectedTwo("find|FIRST|dlfk");
        } catch (IllegalArgumentException e){
            assertEquals("Неверно количество параметров разделенных знаком '|', ожидается 2, но есть: 3",
                    e.getMessage());
        }
    }

    @Test
    public void test_expectedThree(){
        String[] actual = new String[]{"find", "FIRST", "TEST"};
        assertEquals(Arrays.toString(actual), Arrays.toString(correctly.expectedThree("find|FIRST|TEST")));
    }

    @Test
    public void test_expectedThreeWrongInput(){
        try {
            correctly.expectedThree("find|FIRST|TEST|dldld|dljdh");
        } catch (IllegalArgumentException e){
            assertEquals("Неверно количество параметров разделенных знаком '|', ожидается 3, но есть: 5",
                    e.getMessage());
        }
    }

    @Test
    public void test_expectedThreeMin(){
        String[] actual = new String[]{"create", "FIRST", "TEST", "TEST2"};
        assertEquals(Arrays.toString(actual), Arrays.toString(correctly.expectedThreeMin("create|FIRST|TEST|TEST2")));
    }

    @Test
    public void test_expectedThreeMinWrongInput(){
        try {
            correctly.expectedThreeMin("find|FIRST");
        } catch (IllegalArgumentException e){
            assertEquals("Неверно количество параметров разделенных знаком '|', ожидается минимум 3, но есть: 2",
                    e.getMessage());
        }
    }

    @Test
    public void test_expectedMinEven(){
        String[] actual = new String[]{"find", "FIRST", "TEST", "'Hello'"};
        assertEquals(Arrays.toString(actual), Arrays.toString(correctly.expectedMinEven("find|FIRST|TEST|'Hello'", 4)));
    }

    @Test
    public void test_expectedMinEvenTen(){
        String[] actual = new String[]{"find", "FIRST", "TEST", "'Hello'", "TEST2", "'World'", "TEST3", "111", "TEST4"
                , "'Java'", "TEST5", "'Tardis'"};
        assertEquals(Arrays.toString(actual), Arrays.toString(correctly.expectedMinEven("find|first|test|'Hello'|tEst2|" +
                "'World'|Test3|111|TeSt4|'Java'|TesT5|'Tardis'", 4)));
    }

    @Test
    public void test_expectedMinEvenWrongInput(){
        try {
            correctly.expectedMinEven("find|FIRST", 4);
        } catch (IllegalArgumentException e){
            assertEquals("Неверно количество параметров разделенных знаком '|', ожидается минимум 4, но есть: 2",
                    e.getMessage());
        }
    }

    @Test
    public void test_expectedMinEvenWrongInputEven(){
        try {
            correctly.expectedMinEven("find|FIRST|TEST|23|TEST1", 4);
        } catch (IllegalArgumentException e){
            assertEquals("Неверно количество параметров разделенных знаком '|', ожидается четное количество аргументов, но есть: 5",
                    e.getMessage());
        }
    }
}
