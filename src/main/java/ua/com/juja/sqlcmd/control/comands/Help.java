package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.view.View;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Help implements Command {

    private View view;

    public Help(View view){
      this.view = view;

    }

    @Override
    public boolean isProcessed(String command) {
        return  command.equals("help");
    }

    @Override
    public void process(String command) {
         String connect = view.redText("connect") + "\n" +
                "\tКоманда для подключения к соответствующей БД\n" +
                "\tФормат команды: " + view.greenText("connect|username|password") + "\n" +
                "\tгде: \n" +
                "\t\t" + view.greenText("username") + " -  имя пользователя БД (совпвдает с именем БД)\n" +
                "\t\t" + view.greenText("password") + " - пароль пользователя БД\n" +
                "\tФормат вывода: " + view.blueText("текстовое сообщение с результатом выполнения операции.");
         String tables = view.redText("tables") + "\n" +
                 "\tКоманда выводит список всех пользовательских таблиц \n" +
                 "\t\tсодержащихся в БД к которой вы подключены.\n" +
                 "\tФормат команды: " + view.greenText("tables")  + "\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
         String columns = view.redText("columns") + "\n" +
                "\tКоманда выводит список всех колонок  \n" +
                "\t\tсодержащихся в запрашиваемой таблице.\n" +
                "\tФормат команды: " + view.greenText("columns|tableName") + "\n" +
                "\tгде " + view.greenText("tableName") + " - имя запрашиваемой таблицы\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
         String tabletype = view.redText("tabletype") + "\n" +
                "\tКоманда выводит список всех колонок, \n" +
                "\t\t\t\t\tтип данных для соответсвующей колонки, \n" +
                "\t\t\t\t\tвозможность содержать null значение соответсвующей колонки\n" +
                "\t\tв запрашиваемой таблице.\n" +
                "\tФормат команды: " + view.greenText("tabletype|tableName") + "\n" +
                "\tгде " + view.greenText("tableName") + " - имя запрашиваемой таблицы\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
         String columntype = view.redText("columntype") + "\n" +
                 "\tКоманда выводит  колоноку, \n" +
                 "\t\t\t\t\tтип данных для соответсвующей колонки, \n" +
                 "\t\t\t\t\tвозможность содержать null значение соответсвующей колонки\n" +
                 "\t\tв запрашиваемой таблице.\n" +
                 "\tФормат команды: " + view.greenText("columntype|tableName|columnName") + "\n" +
                 "\tгде " + view.greenText("tableName") + " - имя запрашиваемой таблицы\n" +
                 "\t\t" + view.greenText("colummnName") + " - имя столбца в запрашиваемой таблице\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
         String clear = view.redText("clear") + "\n" +
                "\tКоманда очищает содержимое указанной (всей) таблицы\n" +
                "\tФормат команды: " + view.greenText("clear|tableName") + "\n" +
                "\tгде " + view.greenText("tableName") + " - имя очищаемой таблицы\n" +
                "\tФормат вывода: " + view.blueText("текстовое сообщение с результатом выполнения операции");
         String drop = view.redText("drop") + "\n" +
                "\tКоманда удаляет заданную таблицу\n" +
                "\tФормат команды: " + view.greenText("drop|tableName") + "\n" +
                "\tгде " + view.greenText("tableName") + " - имя удаляемой таблицы\n" +
                 "\tФормат вывода: " + view.blueText("текстовое сообщение с результатом выполнения операции");
         String create = view.redText("create") + "\n" +
                "\tКоманда создает новую таблицу с заданными полями\n" +
                "\tФормат команды: " + view.greenText("create|tableName|column1(data type(data size)) nullable|column2(data type(data size)) \n" +
                 "nullable|...|columnN(data type(data size)) nullable") + "\n" +
                "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                "\t" + view.greenText("column1") + " - имя первого столбца записи\n" +
                "\t\t" + view.greenText("(data type(data size))")+ " - (тип данных колонки(максимальный размер данных для колонки))\n" +
                "\t\t\t" + view.greenText("nullable:") + "\n" +
                "\t\t\t\t" + view.greenText("null") + " - при создании записи в столбце значение может содержать null\n" +
                "\t\t\t\t" + view.greenText("not null") + " - при создании записи в столбце значение НЕ может содержать null\n" +
                "\t" + view.greenText("column2") + " - имя второго столбца записи\n" +
                "\t\t-//-\n" +
                "\t" + view.greenText("columnN") + " - имя n-го столбца записи\n" +
                "\t\t-//-\n" +
                 "\tФормат вывода: " + view.blueText("текстовое сообщение с результатом выполнения операции");
         String find = view.redText("find") + "\n" +
                "\tКоманда для получения содержимого указанной таблицы\n" +
                "\tФормат команды: " + view.greenText("find|tableName") + "\n" +
                 "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
        String filetable = view.redText("filetable") + "\n" +
                "\tКоманда для сохранения содержимого указанной таблицы в файл\n" +
                "\tФормат команды: " + view.greenText("filetable|tableName") + "\n" +
                 "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате + текстовое сообщение с результатом выполнения операции");
         String findsettings = view.redText("findsettings") + "\n" +
                "\tКоманда для получения содержимого указанной таблицы по определенным критериям\n" +
                "\tФормат команды: " + view.greenText("find|tableName|columnName|value|column2|value2|...|columnN|valueN") + "\n" +
                 "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                "\t\t" + view.greenText("column1") + " - имя первого столбца\n" +
                "\t\t" + view.greenText("value1") + " - значение первого столбца\n" +
                "\t\t" + view.greenText("column2") + " - имя второго столбца\n" +
                "\t\t" + view.greenText("value2") + " - значение второго столбца\n" +
                "\t\t" + view.greenText("columnN") + " - имя n-го столбца\n" +
                "\t\t" + view.greenText("valueN") + " - имя n-го столбца\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
         String insert = view.redText("insert") + "\n" +
                "\tКоманда для вставки одной строки в заданную таблицу\n" +
                "\tФормат команды: " + view.greenText("insert|tableName|column1|value1|column2|value2|...|columnN|valueN") + "\n" +
                 "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                 "\t" + view.greenText("column1") + " - имя первого столбца записи\n" +
                 "\t" + view.greenText("value1") + " - значение первого столбца записи\n" +
                 "\t" + view.greenText("column2") + " - имя второго столбца записи\n" +
                 "\t" + view.greenText("value2") + " - значение второго столбца записи\n" +
                 "\t" + view.greenText("columnN") + " - имя n-го столбца записи\n" +
                 "\t" + view.greenText("valueN") + " - имя n-го столбца записи\n" +
                 "\t\tNUMBER --- записывается число без ковычек   columnName|123\n" +
                 "\t\tVARCHAR2 - строковое значение записывается в ковычках   columnName|'Hello'\n" +
                 "\t\tDATE ----- даты записываются следующим образом  to_date('20160321','YYYYMMDD')\n" +
                 "\tФормат вывода: " + view.blueText("текстовое сообщение с результатом выполнения операции");
         String update = view.redText("update") + "\n" +
                "\tКоманда обновит запись, установив значение column2 = value2, для которой соблюдается условие column1 = value1\n" +
                "\tФормат команды: " + view.greenText("update|tableName|column1|value1|column2|value2") + "\n" +
                 "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                "\t" + view.greenText("column1") + " - имя столбца записи которое проверяется\n" +
                "\t" + view.greenText("value1") + " - значение которому должен соответствовать столбец column1 для обновляемой записи\n" +
                "\t" + view.greenText("column2") + " - имя обновляемого столбца записи\n" +
                "\t" + view.greenText("value2") + " - значение обновляемого столбца записи\n" +
                "\t" + view.greenText("columnN") + " - имя n-го обновляемого столбца записи\n" +
                "\t" + view.greenText("valueN")+ " - значение n-го обновляемого столбца записи\n" +
                "\t\tNUMBER --- записывается число без ковычек   columnName|123\n" +
                "\t\tVARCHAR2 - строковое значение записывается в ковычках   columnName|'Hello'\n" +
                "\t\tDATE ----- даты записываются следующим образом  to_date('20160321','YYYYMMDD')\n" +
                "\t" + view.redText("Количесьво обновляемых параметров и параметров по которым обновлять должно быть одинаковым!") + "\n" +
                "\tФормат вывода: " + view.blueText("табличный, как при find со старыми значениями обновленных записей.");
         String delete = view.redText("delete") + "\n" +
                "\tКоманда удаляет одну или несколько записей для которых соблюдается условие column = value\n" +
                "\tФормат команды: " + view.greenText("delete|tableName|column|value") + "\n" +
                 "\tгде: " + view.greenText("tableName") + " - имя таблицы\n" +
                "\t" + view.greenText("column") + " - имя столбца записи которое проверяется\n" +
                "\t" + view.greenText("value") + " - значение которому должен соответствовать столбец column1 для удаляемой записи\n" +
                "\tФормат вывода: " + view.blueText("табличный, как при find со старыми значениями удаляемых записей.");
         String readQuery = view.redText("readQuery") + "\n" +
                 "\tКоманда для ввода SQL запроса\n" +
                 "\tФормат команды: " + view.greenText("readQuery|SQLQuery") + view.redText("(Только чтение из БД)") +  "\n" +
                 "\tгде: SQLQuery - ваш SQL запрос\n" +
                 "\tФормат вывода: " + view.blueText("табличка в консольном формате");
         String cudQuery = view.redText("cudQuery") + "\n" +
                 "\tФормат команды: " + view.greenText("cudQuery|SQLQuery") + " (Для внесения изменений в таблицу)\n" +
                 "\tгде: " + view.greenText("SQLQuery") + " - ваш SQL запрос\n" +
                 "\tФормат вывода: " + view.blueText("текстовое сообщение с результатом выполнения операции");
         String history = view.redText("history") + "\n" +
                "\tКоманда для получения истории работы с приложением\n" +
                 "\tФормат команды: " + view.greenText("history") + "\n" +
                "\tФормат вывода: " + view.blueText("дата и время использования команды -> действия") + "\n" +
                "\t\t\t\t\t\t\t" + view.blueText("результат выполнения команды");
         String help = view.redText("help") + "\n" +
                "\tКоманда выводит в консоль список всех доступных команд\n" +
                "\tФормат команды: " + view.greenText("help") + "\n" +
                "\tФормат вывода: " + view.blueText("текст, описания команд");
         String exit = view.redText("exit") + "\n" +
                "\tКоманда для отключения от БД и выход из приложения\n" +
                "\tФормат команды: " + view.greenText("exit") + "\n" +
                 "\tФормат вывода: " + view.blueText("текстовое сообщение");
        view.write(connect);
        view.write(tables);
        view.write(columns);
        view.write(tabletype);
        view.write(columntype);
        view.write(clear);
        view.write(drop);
        view.write(create);
        view.write(find);
        view.write(filetable);
        view.write(findsettings);
        view.write(insert);
        view.write(update);
        view.write(delete);
        view.write(readQuery);
        view.write(cudQuery);
        view.write(history);
        view.write(help);
        view.write(exit);
    }
}
