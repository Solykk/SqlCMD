package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.view.View;

public class Services {

    private final ViewService viewService = new ViewService();
    private final Correctly correctly = new Correctly();
    private final TablePrinter tablePrinter = new TablePrinter();
    private final SettingsHelper settingsHelper = new SettingsHelper();

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
