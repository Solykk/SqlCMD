package ua.com.juja.sqlcmd.control.commands;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.Services;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Services services;
    private Connect connect;
    private Correctly correctly;
    private ViewService viewService;

    @Before
    public void setUp(){
        manager = mock(JDBCDatabaseManager.class);
        view = mock(Console.class);
        services = mock(Services.class);
        viewService = mock(ViewService.class);
        viewService.setView(view);
        correctly = mock(Correctly.class);
        connect = new Connect(manager, services);

    }

    @Test
    @Ignore
    public void test_Connect() throws SQLException {
        //given
        String[] data = {"connect", "test", "pass"};
        when(services.getCorrectly()).thenReturn(correctly);
        when(correctly.expectedThree(anyString())).thenReturn(data);

        //when
        connect.process("");

        //then
        shouldPrint("");
    }

    @Test
    @Ignore
    public void test_ConnectWithSqlException() throws SQLException {
        //given
        String[] data = {"connect", "test", "pass"};
        when(services.getCorrectly()).thenReturn(correctly);
        when(services.getViewService()).thenReturn(viewService);
        when(correctly.expectedThree(anyString())).thenReturn(data);

        doThrow(new SQLException("message")).when(manager).connect(anyString(), anyString());

        //when
        connect = new Connect(manager, services);
        connect.process("");

        //then
        shouldPrint("");
    }

    @Test
    @Ignore
    public void test_isProcessed() throws SQLException {
        String com = "connect|";

        assertTrue(connect.isProcessed(com));
    }

    @Test
    @Ignore
    public void test_ConnectT() throws SQLException {
        //given
        String[] data = {"connect", "test", "pass"};
        when(services.getCorrectly()).thenReturn(correctly);
        when(services.getViewService()).thenReturn(viewService);
        when(correctly.expectedThree(anyString())).thenReturn(data);

        //when
        connect = new Connect(manager, services);
        connect.process("");

        //then
        shouldPrint("");
    }

    private void shouldPrint(String expected) throws SQLException {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).writeAndHistory(captor.capture(), anyString());
        assertEquals(expected, captor.getAllValues().toString());
    }
}