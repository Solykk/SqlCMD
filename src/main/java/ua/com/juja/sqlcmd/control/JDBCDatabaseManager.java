package ua.com.juja.sqlcmd.control;

import ua.com.juja.sqlcmd.service.History;
import ua.com.juja.sqlcmd.view.Console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by Solyk on 26.01.2017.
 */
public class JDBCDatabaseManager implements DatabaseManager{

    private Connection connection;
    private final String url = "jdbc:oracle:thin:/@localhost:1521:XE";
    private History history;




    public JDBCDatabaseManager(){
        Locale.setDefault(Locale.ENGLISH);
        this.connection = null;
        this.history = new History();
    }

    public boolean connect(String userName, String dbPassword){
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            throw new RuntimeException("Please add jdbc jar to project.", e);
        }

        try

        {

            connection = DriverManager.getConnection(url, userName, dbPassword);

            new Console().write("\t\t\t\t\t\t\t\tУспех, вы подключились к базе ");
            new Console().write(connection.getMetaData().getDatabaseProductVersion());

            return true;

        } catch (SQLException e){

            connection = null;
            new Console().write("Неудача, не удалось подключиться к базе ");

            return false;
        }

    }


}

