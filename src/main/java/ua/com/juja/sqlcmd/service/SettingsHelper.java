package ua.com.juja.sqlcmd.service;

import java.util.ArrayList;
import java.util.List;

public class SettingsHelper {

    private void toSettings(String[] data, List<String[]> settings, int index) {
        String[] tmp = new String[2];
        tmp[0] = data[index];
        tmp[1] = data[index + 1];
        settings.add(tmp);
    }

    public List<String[]> getSettings(String[] data) {
        List<String[]> settings = new ArrayList<>();
        int index = 2;
        while (index != data.length) {
            toSettings(data, settings, index);
            index += 2;
        }
        return settings;
    }

    public void getSetUpdate(String[] data, List<String[]> forUpdate, List<String[]> howUpdate) {
        int indexFor = 2;
        int indexHow = data.length - (data.length - 2)/2;

        while (indexFor != indexHow){
            toSettings(data, forUpdate, indexFor);
            indexFor += 2;
        }
        while (indexHow  != data.length) {
            toSettings(data, howUpdate, indexHow);
            indexHow += 2;
        }
    }

    public List<String> getSetCreate(String[] data) {
        List<String> settings = new ArrayList<>();
        int index = 2;
        while (index  != data.length){
            settings.add(data[index]);
            index++;
        }
        return settings;
    }

    public List<String[]> addSettings(String[] data, List<String[]> settings) {
        int index = 2;
        String temp = "";
        if(settings.size() == 1){
            temp += settings.get(0)[0];
        }

        while (index != data.length) {
            if(!temp.equals("")){
                if(temp.equals(data[index])){
                    index += 2;
                    continue;
                }
            }
            toSettings(data, settings, index);
            index += 2;
        }
        return settings;
    }
 }