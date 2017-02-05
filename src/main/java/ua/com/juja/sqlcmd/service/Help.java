package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.control.comands.Comand;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Help implements Comand{

    private View view;
    private String connect = "сonnect\n" +
            "\tКоманда для подключения к соответствующей БД\n" +
            "\tФормат команды: connect|username|password\n" +
            "\tгде: \n" +
            "\t\tusername -  имя пользователя БД (совпвдает с именем БД)\n" +
            "\t\tpassword - пароль пользователя БД\n" +
            "\tФормат вывода: текстовое сообщение с результатом выполнения операции.";
    private String tables = "tables\n" +
            "\tКоманда выводит список всех пользовательских таблиц \n" +
            "\t\tсодержащихся в БД к которой вы подключены.\n" +
            "\tФормат вывода: табличка в консольном формате\t";
    private String columns = "columns\n" +
            "\tКоманда выводит список всех колонок  \n" +
            "\t\tсодержащихся в запрашиваемой таблице.\n" +
            "\tФормат команды: columns|tableName\n" +
            "\tгде tableName - имя запрашиваемой таблицы\n" +
            "\tФормат вывода: табличка в консольном формате";
    private String tabletype = "tabletype\n" +
            "\tКоманда выводит список всех колонок, \n" +
            "\t\t\t\t\tтип данных для соответсвующей колонки, \n" +
            "\t\t\t\t\tвозможность содержать null значение соответсвующей колонки\n" +
            "\t\tв запрашиваемой таблице.\n" +
            "\tФормат команды: tabletype|tableName\n" +
            "\tгде tableName - имя запрашиваемой таблицы\n" +
            "\tФормат вывода: табличка в консольном формате";
    private String columntype = "columntype\n" +
            "\tФормат команды: columntype|tableName|columnName\n" +
            "\tгде tableName - имя запрашиваемой таблицы\n" +
            "\t\tcolummnName - имя столбца в запрашиваемой таблице\n" +
            "\tКоманда выводит  колоноку, \n" +
            "\t\t\t\t\tтип данных для соответсвующей колонки, \n" +
            "\t\t\t\t\tвозможность содержать null значение соответсвующей колонки\n" +
            "\t\tв запрашиваемой таблице.\n" +
            "\tФормат вывода: табличка в консольном формате";
    private String clear = "clear\n" +
            "\tКоманда очищает содержимое указанной (всей) таблицы\n" +
            "\tФормат: clear|tableName\n" +
            "\tгде tableName - имя очищаемой таблицы\n" +
            "\tФормат вывода: текстовое сообщение с результатом выполнения операции";
    private String drop = "drop\n" +
            "\tКоманда удаляет заданную таблицу\n" +
            "\tФормат: drop|tableName\n" +
            "\tгде tableName - имя удаляемой таблицы\n" +
            "\tФормат вывода: текстовое сообщение с результатом выполнения операции";
    private String create = "create\n" +
            "\tКоманда создает новую таблицу с заданными полями\n" +
            "\tФормат: create|tableName|column1(data type(data size)) nullable|column2(data type(data size)) nullable|...|columnN(data type(data size)) nullable\n" +
            "\tгде: tableName - имя таблицы\n" +
            "\tcolumn1 - имя первого столбца записи\n" +
            "\t\t(data type(data size)) - (тип данных колонки(максимальный размер данных для колонки))\n" +
            "\t\t\tnullable:\n" +
            "\t\t\t\tnull - при создании записи в столбце значение может содержать null\n" +
            "\t\t\t\tnot null - при создании записи в столбце значение НЕ может содержать null\n" +
            "\tcolumn2 - имя второго столбца записи\n" +
            "\t\t-//-\n" +
            "\tcolumnN - имя n-го столбца записи\n" +
            "\t\t-//-\n" +
            "\tФормат вывода: текстовое сообщение с результатом выполнения операции";
    private String find = "find \n" +
            "\tКоманда для получения содержимого указанной таблицы\n" +
            "\tФормат: find|tableName\n" +
            "\tгде tableName - имя таблицы\n" +
            "\tФормат вывода: табличка в консольном формате";
    private String findsettings = "findsettings\n" +
            "\tКоманда для получения содержимого указанной таблицы по определенным критериям\n" +
            "\tФормат: find|tableName|columnName|value|column2|value2|...|columnN|valueN\n" +
            "\tгде tableName - имя таблицы\n" +
            "\t\tcolumn1 - имя первого столбца записи\n" +
            "\t\tvalue1 - значение первого столбца записи\n" +
            "\t\tcolumn2 - имя второго столбца записи\n" +
            "\t\tvalue2 - значение второго столбца записи\n" +
            "\t\tcolumnN - имя n-го столбца записи\n" +
            "\tФормат вывода: табличка в консольном формате";
    private String insert = "insert\n" +
            "\tКоманда для вставки одной строки в заданную таблицу\n" +
            "\tФормат: insert|tableName|column1|value1|column2|value2|...|columnN|valueN\n" +
            "\tгде: tableName - имя таблицы\n" +
            "\tcolumn1 - имя первого столбца записи\n" +
            "\tvalue1 - значение первого столбца записи\n" +
            "\tcolumn2 - имя второго столбца записи\n" +
            "\tvalue2 - значение второго столбца записи\n" +
            "\tcolumnN - имя n-го столбца записи\n" +
            "\tvalueN - значение n-го столбца записи\n" +
            "\tФормат вывода: текстовое сообщение с результатом выполнения операции";
    private String update = "update\n" +
            "\tКоманда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1\n" +
            "\tФормат: update|tableName|column1|value1|column2|value2\n" +
            "\tгде: tableName - имя таблицы\n" +
            "\tcolumn1 - имя столбца записи которое проверяется\n" +
            "\tvalue1 - значение которому должен соответствовать столбец column1 для обновляемой записи\n" +
            "\tcolumn2 - имя обновляемого столбца записи\n" +
            "\tvalue2 - значение обновляемого столбца записи\n" +
            "\tcolumnN - имя n-го обновляемого столбца записи\n" +
            "\tvalueN - значение n-го обновляемого столбца записи\n" +
            "\tФормат вывода: табличный, как при find со старыми значениями обновленных записей.";
    private String delete = "delete\n" +
            "\tКоманда удаляет одну или несколько записей для которых соблюдается условие column = value\n" +
            "\tФормат: delete|tableName|column|value\n" +
            "\tгде: tableName - имя таблицы\n" +
            "\tColumn - имя столбца записи которое проверяется\n" +
            "\tvalue - значение которому должен соответствовать столбец column1 для удаляемой записи\n" +
            "\tФормат вывода: табличный, как при find со старыми значениями удаляемых записей.";
    private String query = "query\n" +
            "\tКоманда для ввода SQL запроса\n" +
            "\tФормат: readQuery|SQLQuery (Только чтение из БД)\n" +
            "\t\t\t\tФормат вывода: табличка в консольном формате\t\n" +
            "\t\t\tcudQuery|SQLQuery (Для внесения изменений в таблицу)\n" +
            "\t\t\t\tФормат вывода: текстовое сообщение с результатом выполнения операции";
    private String help = "help \n" +
            "\tКоманда выводит в консоль список всех доступных команд\n" +
            "\tФормат: help (без параметров)\n" +
            "\tФормат вывода: текст, описания команд";
    private String exit = "exit \n" +
            "\tКоманда для отключения от БД и выход из приложения\n" +
            "\tФормат: exit (без параметров)\n" +
            "\tФормат вывода: текстовое сообщение с результатом выполнения операции";



    public Help(View view){
      this.view = view;

    }

    @Override
    public boolean isProcessed(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write(connect);
        view.write(tables);
        view.write(columns);
        view.write(tabletype);
        view.write(columntype);
        view.write(clear);
        view.write(drop);
        view.write(create);
        view.write(find);
        view.write(findsettings);
        view.write(insert);
        view.write(update);
        view.write(delete);
        view.write(query);
        view.write(help);
        view.write(exit);
    }
}
