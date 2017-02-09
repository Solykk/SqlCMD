package ua.com.juja.sqlcmd.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Solyk on 26.01.2017.
 */
public class ConnectionTest {

    private DatabaseManager manager;
    private View view;

        @Before
        public void start(){
            manager = new JDBCDatabaseManager();
            view = new Console();
        }



        @Test
        public void connection(){
            try {
                manager.connect("hr", "hr");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Assert.assertTrue(manager.isConnected());


        }

        @Test
        public void fail_connection(){
            try {
                manager.connect("ewe", "werer");
            } catch (SQLException e) {

            }

            Assert.assertFalse(manager.isConnected());
        }


       @Test
       public void getAllTableNames(){
           try {
               manager.connect("hr", "hr");
           } catch (SQLException e) {

           }

           Table table = null;
           try {
               table = manager.getAllTableNames();

           } catch (SQLException e) {

           }

           String result = view.printTable(table);

           assertEquals(
                   "---------------\n" +
                   "| ALL_TABLES  |\n" +
                   "---------------\n" +
                   "| TABLE_NAME  |\n" +
                   "---------------\n" +
                   "|   REGIONS   |\n" +
                   "|  LOCATIONS  |\n" +
                   "| DEPARTMENTS |\n" +
                   "|    JOBS     |\n" +
                   "|  EMPLOYEES  |\n" +
                   "| JOB_HISTORY |\n" +
                   "|   SERTEE    |\n" +
                   "|   MYTABLE   |\n" +
                   "|    SERTE    |\n" +
                   "|  COUNTRIES  |\n" +
                   "---------------\n",result);

        }
//
//    @Test
//    public void getAllTableNames_fail(){
//        manager.connect("hr", "hr");
//        ArrayList<String> expectedResult =  new ArrayList<>();
//
//        expectedResult.add("REGIONS");
//        expectedResult.add("LOCATIONS");
//        expectedResult.add("DEPARTMENTS");
//        expectedResult.add("JOBS");
//        expectedResult.add("COUNTRIES");
//
//        ArrayList<String> result = manager.getAllTableNames();
//
//       Assert.assertFalse(result.equals(expectedResult));
//
//    }
//
//    @Test
//    public void connection_null(){
//
//        ArrayList<String> result = manager.getAllTableNames();
//
//        Assert.assertNull(result);
//
//    }

}
