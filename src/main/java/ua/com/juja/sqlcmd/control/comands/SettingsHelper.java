package ua.com.juja.sqlcmd.control.comands;

import java.util.ArrayList;

/**
 * Created by Solyk on 07.02.2017.
 */
public class SettingsHelper {

        public  static void toSettings(String[] data, ArrayList<String[]> settings, int index) {
            String[] tmp = new String[2];
            tmp[0] = data[index];
            tmp[1] = data[index + 1];
            settings.add(tmp);
        }
 }
