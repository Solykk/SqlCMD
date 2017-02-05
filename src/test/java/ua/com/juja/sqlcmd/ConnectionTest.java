package ua.com.juja.sqlcmd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;

import java.util.ArrayList;

/**
 * Created by Solyk on 26.01.2017.
 */
public class ConnectionTest {

    private JDBCDatabaseManager manager;

        @Before
        public void start(){
            manager = new JDBCDatabaseManager();
        }



        @Test
        public void connection(){
            Assert.assertTrue(manager.connect("hr", "hr"));


        }
        @Test
        public void fail_connection(){
            Assert.assertFalse(manager.connect("sqlcmdd", "sqlcmd"));
        }


//       @Test
//       public void getAllTableNames(){
//           manager.connect("hr", "hr");
//        ArrayList<String> expectedResult =  new ArrayList<>();
//
//           expectedResult.add("REGIONS");
//           expectedResult.add("LOCATIONS");
//           expectedResult.add("DEPARTMENTS");
//           expectedResult.add("JOBS");
//           expectedResult.add("EMPLOYEES");
//           expectedResult.add("JOB_HISTORY");
//           expectedResult.add("COUNTRIES");
//
//           ArrayList<String> result = manager.getAllTableNames();
//
//        Assert.assertTrue(result.equals(expectedResult));
//
//        }

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

//    @Test
//    public void connection_null(){
//
//        ArrayList<String> result = manager.getAllTableNames();
//
//        Assert.assertNull(result);
//
//    }

}
