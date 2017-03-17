package ua.com.juja.sqlcmd.service;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

public class DropAllHelper {

    public static void dropAll(){
        DatabaseManager purge = new JDBCDatabaseManager();
        try {
            purge.connect("test", "pass");
            purge.cudQuery("BEGIN\n" +
                    "   FOR cur_rec IN (SELECT object_name, object_type\n" +
                    "                     FROM user_objects\n" +
                    "                    WHERE object_type IN\n" +
                    "                             ('TABLE',\n" +
                    "                              'VIEW',\n" +
                    "                              'PACKAGE',\n" +
                    "                              'PROCEDURE',\n" +
                    "                              'FUNCTION',\n" +
                    "                              'SEQUENCE'\n" +
                    "                             ))\n" +
                    "   LOOP\n" +
                    "      BEGIN\n" +
                    "         IF cur_rec.object_type = 'TABLE'\n" +
                    "         THEN\n" +
                    "            EXECUTE IMMEDIATE    'DROP '\n" +
                    "                              || cur_rec.object_type\n" +
                    "                              || ' \"'\n" +
                    "                              || cur_rec.object_name\n" +
                    "                              || '\" CASCADE CONSTRAINTS';\n" +
                    "         ELSE\n" +
                    "            EXECUTE IMMEDIATE    'DROP '\n" +
                    "                              || cur_rec.object_type\n" +
                    "                              || ' \"'\n" +
                    "                              || cur_rec.object_name\n" +
                    "                              || '\"';\n" +
                    "         END IF;\n" +
                    "      EXCEPTION\n" +
                    "         WHEN OTHERS\n" +
                    "         THEN\n" +
                    "            DBMS_OUTPUT.put_line (   'FAILED: DROP '\n" +
                    "                                  || cur_rec.object_type\n" +
                    "                                  || ' \"'\n" +
                    "                                  || cur_rec.object_name\n" +
                    "                                  || '\"'\n" +
                    "                                 );\n" +
                    "      END;\n" +
                    "   END LOOP;\n" +
                    "END;");
            purge.disconnect();
        } catch (Exception e){
            //do nothing
        }
    }

    public static void purgeRecycle(){
        DatabaseManager purge = new JDBCDatabaseManager();
        try {
            purge.connect("test", "pass");
            purge.cudQuery("PURGE RECYCLEBIN");
            purge.disconnect();
        } catch (Exception e){
            //do nothing
        }
    }

}
