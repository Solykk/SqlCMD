package ua.com.juja.sqlcmd.control.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Services services;
    private Connect connect;

    @Before
    public void setUp(){
        manager = mock(JDBCDatabaseManager.class);
        view = mock(Console.class);
        services = mock(Services.class);
        services.setView(view);

        connect = new Connect(manager, services);
    }

    @Test
    public void test_Connect() throws SQLException {
        //given
        String[] data = {"connect", "test", "pass"};
        when(services.getCorrectly().expectedThree(anyString())).thenReturn(data);

        //when
        connect.process("");

        //then
        shouldPrint("");
    }

    @Test
    public void test_ConnectWithSqlException() throws SQLException {
        //given
        String[] data = {"connect", "test", "pass"};
        when(services.getCorrectly().expectedThree(anyString())).thenReturn(data);
        doThrow(new SQLException("message")).when(manager).connect(anyString(), anyString());

        //when
        connect.process("");

        //then
        shouldPrint("");
    }

    @Test
    public void test_isProcessed() throws SQLException {
        String com = "connect|";

        assertTrue(connect.isProcessed(com));
    }

    private void shouldPrint(String s) throws SQLException {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).writeAndHistory(captor.capture(), anyString());
        doThrow(new SQLException("message")).when(manager).connect(anyString(), anyString());
    }
}