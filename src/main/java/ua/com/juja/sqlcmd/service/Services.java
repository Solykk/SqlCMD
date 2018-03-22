package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

public class Services {

    private ViewService viewService = new ViewService();
    private Correctly correctly = new Correctly();
    private TablePrinter tablePrinter = new TablePrinter();
    private SettingsHelper settingsHelper = new SettingsHelper();

    public void setView(View view) {
        this.viewService.setView(view);
        this.tablePrinter.setView(view);
    }

    public ViewService getViewService() {
        return viewService;
    }

    public Correctly getCorrectly() {
        return correctly;
    }

    public TablePrinter getTablePrinter() {
        return tablePrinter;
    }

    public SettingsHelper getSettingsHelper() {
        return settingsHelper;
    }
}