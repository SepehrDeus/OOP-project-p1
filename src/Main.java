import input.MenuControl;
import jdbc.Database;
import jdbc.JDBC;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        JDBC.run_jdbc();
        MenuControl.setMenuNum(0);
        MenuControl.setRun(true);
        MenuControl.inputScanner();
    }
}
