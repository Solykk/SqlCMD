package ua.com.juja.sqlcmd.control;

/**
 * Created by Solyk on 17.02.2017.
 */
public class WhileCTRL {

    private boolean value;

    public WhileCTRL(){
        this.value = true;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

}
