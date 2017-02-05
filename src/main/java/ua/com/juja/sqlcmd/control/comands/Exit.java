package ua.com.juja.sqlcmd.control.comands;

/**
 * Created by Solyk on 26.01.2017.
 */
public class Exit implements Comand{

    @Override
    public boolean isProcessed(String command) {
        return false;
    }

    @Override
    public void process(String command) {

    }

    static void  exit(){
        System.exit(0);
    }
}
