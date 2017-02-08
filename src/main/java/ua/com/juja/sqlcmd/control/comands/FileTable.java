package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Solyk on 08.02.2017.
 */
public class FileTable implements Command{

    private DatabaseManager manager;
    private View view;

    public FileTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("filetable|");
    }

    @Override
    public void process(String command) {
        String [] data = command.split("\\|");
        if(data.length != 2){
            throw new IllegalArgumentException("Неверно количество параметров разделенных знаком '|', " +
                    "ожидается 2, но есть: " + data.length);
        }

        String tableName = data[1];

        History.cache.add(History.getDate() + " " + "Запись содержимого таблицы: " + tableName
                + " в файл " + view.yellowText(FileTable.class.getSimpleName().toLowerCase()));

        try {
            Table request = manager.readTable(tableName);
            String dataTable = view.printTable(request);
            view.write("Сохранить эти данные в файл? Если да, введите " + view.yellowText("y"));
            String response = view.read();
                if (response.equals("y")){
                    view.write("Имя файла будет соответствовать имени таблицы. Если согланы, введите " + view.yellowText("y"));
                    view.write("Если хотите переименовать файл, нажмите Enter");
                    String reName = view.read();
                    if (reName.equals("y")){
                        File file = new File("src/main/resources", tableName + ".txt");
                        fileExistChecker(dataTable, tableName, file);
                    } else {
                        reNameWriter(dataTable);
                    }
                } else {
                    History.cache.add(view.requestTab(view.blueText("Запись отменена")));
                }

        } catch (Exception e) {
            History.cache.add(view.requestTab(view.redText("Неудача " + view.redText(e.getMessage()))));
            view.write(view.redText("Ошибка. Не удалось сохранить таблицу ( " + tableName + " ) "
                    + view.redText(e.getMessage())));
        }

    }

    private void reNameWriter(String dataTable) throws IOException {
        view.write("Введите название файла: ");
        String name = view.read();
        File file = new File("src/main/resources", name + ".txt");
        fileExistChecker(dataTable, name, file);
    }

    private void fileExistChecker(String dataTable, String name, File file) throws IOException {
        if(file.exists()){
            view.write("Файл с именем " + name + " уже существует! Перезаписать его? Если да, введите " + view.yellowText("y"));
            view.write("Если хотите переименовать файл, нажмите Enter");
            String read = view.read();
            if(read.equals("y")){
                nameOkWriter(name, dataTable, file);
            } else {
                reNameWriter(dataTable);
            }
        } else {
            nameOkWriter(name, dataTable, file);
        }
    }

    private void nameOkWriter(String tableName, String dataTable, File file) throws IOException {
        fileInput(dataTable, tableName, file);
        view.write(view.blueText("Данные сохранены в файл"));
        History.cache.add(view.requestTab(view.blueText("Успех")));
    }

    private void fileInput(String dataTable, String name, File file) throws IOException {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw e;
        }
        try(FileWriter writer = new FileWriter("src/main/resources/" + name + ".txt"))
        {
            writer.write(dataTable);
            writer.flush();
        }
    }
}
