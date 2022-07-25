package input;

import repository.UserRepository;

import java.sql.SQLException;

public class RegisterMenu {
    public static final int MENU_NUM = 0;

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help"))
            System.out.println("""
                    REGISTER MENU
                    * register
                    * login

                    help
                    exit
                    """
            );


        // exit
        else if (input.equalsIgnoreCase("exit")){
            MenuControl.setRun(false);
        }


        // register
        else if (input.equalsIgnoreCase("register")) {
            try {
                UserRepository.register();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // login
        else if (input.equalsIgnoreCase("login")) {
            try {
                UserRepository.login();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
