package ua.com.juja.sqlcmd.control.comands;

import ua.com.juja.sqlcmd.control.DatabaseManager;
import ua.com.juja.sqlcmd.model.Table;
import ua.com.juja.sqlcmd.service.Correctly;
import ua.com.juja.sqlcmd.service.ViewService;
import ua.com.juja.sqlcmd.view.View;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileTable implements Command{

    private DatabaseManager manager;
    private View view;
    private Correctly correctly;
    private ViewService viewService;

    public FileTable(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
        this.correctly = new Correctly();
        this.viewService = new ViewService(view);
    }

    @Override
    public boolean isProcessed(String command) {
        return command.startsWith("filetable|");
    }

    @Override
    public void process(String command) {

        String tableName = correctly.expectedTwo(command);

        try {
            Table request = manager.read(tableName);
            String dataTable = view.printTable(request);

            String response = saveAction();
            if (response.equalsIgnoreCase("y")){

                String reName = nameAction();
                if (reName.equalsIgnoreCase("y")){
                    saveFile(tableName, dataTable);
                } else {
                    reNameWriter(dataTable);
                }
            } else {
                viewService.fileTabComTryAbort(tableName);
            }
        } catch (Exception e) {
            viewService.fileTabComCatch(tableName, e.getMessage());
        }
    }

    private void saveFile(String tableName, String dataTable) throws IOException {
        File file = new File("src/main/resources", tableName + ".txt");
        fileExistChecker(dataTable, tableName, file);
    }

    private String nameAction() {
        view.write("Имя файла будет соответствовать имени таблицы. Если согланы, введите " + "Y");
        view.write("Если хотите переименовать файл, нажмите Enter");
        return view.read();
    }

    private String saveAction() {
        view.write("Сохранить эти данные в файл? Y/N");
        return view.read();
    }

    private void reNameWriter(String dataTable) throws IOException {
        view.write("Введите название файла: ");
        String name = view.read();
        if (name.equals("")){
            view.write("Название файла не может быть пустым! ");
            reNameWriter(dataTable);
        }

        CharSequence [] taboo = new CharSequence[]{"/", "\\", "<", ">", "?",":", "|" , "*", "\""};
        boolean isCont = false;

        for (CharSequence chars: taboo) {
            if (name.contains(chars)){
                isCont = true;
                break;
            }
        }

        if (isCont){
            view.write("Название файла не мжет содержать " + "/\\<>?:|*\"");
            reNameWriter(dataTable);
            return;
        }
        saveFile(name, dataTable);
    }

    private void fileExistChecker(String dataTable, String name, File file) throws IOException {
        if(file.exists()){
            String read = existAction(name);

            if(read.equalsIgnoreCase("y")){
                nameOkWriter(name, dataTable, file);
            } else {
                reNameWriter(dataTable);
            }
        } else {
            nameOkWriter(name, dataTable, file);
        }
    }

    private String existAction(String name) {
        view.write("Файл с именем " + name + " уже существует! Перезаписать его? Если да, введите " + "Y");
        view.write("Если хотите переименовать файл, нажмите Enter");
        return view.read();
    }

    private void nameOkWriter(String tableName, String dataTable, File file) throws IOException {
        fileInput(dataTable, tableName, file);
        viewService.fileTabComTrySuc(tableName);
    }

    private void fileInput(String dataTable, String name, File file) throws IOException {

        if(file.createNewFile()) {
            try (FileWriter writer = new FileWriter("src/main/resources/" + name + ".txt")) {
                writer.write(dataTable);
                writer.flush();
            }
        }
    }
}
