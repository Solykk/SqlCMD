package ua.com.juja.sqlcmd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.control.JDBCDatabaseManager;

/**
 * Created by Solyk on 26.01.2017.
 */
public class ConnectionTest {

    private DatabaseManager manager;

        @Before
        public void start(){
            manager = new JDBCDatabaseManager();
        }



        @Test
        public void connection(){
            Assert.assertTrue(manager.connect("user", "pass"));


        }
        @Test
        public void fail_connection(){
            Assert.assertFalse(manager.connect("sqlcmdd", "sqlcmd"));
        }


}
