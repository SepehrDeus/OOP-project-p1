package input;

import repository.UserRepository;

import java.sql.SQLException;

public class EditMenu {
    public static final int MENU_NUM = 3;
    private static String userID;

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help"))
            System.out.println("""
                    EDIT
                    * change username
                    * change password
                    * change biography
                    * change email
                    * change website
                    * main menu

                    help
                    exit
                    """
            );

        // exit
        else if (input.equalsIgnoreCase("exit")) {
            MenuControl.setRun(false);
        }

        // change username
        else if (input.equalsIgnoreCase("change username")) {
            try {
                UserRepository.update_username(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // change password
        else if (input.equalsIgnoreCase("change password")) {
            try {
                UserRepository.update_password(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // change biography
        else if (input.equalsIgnoreCase("change biography")) {
            try {
                UserRepository.update_biography(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // change email
        else if (input.equalsIgnoreCase("change email")) {
            try {
                UserRepository.update_email(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // change website
        else if (input.equalsIgnoreCase("change website")) {
            try {
                UserRepository.update_website(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // main menu
        else if (input.equalsIgnoreCase("main menu")) {
            EditMenu.setUserID(null);
            MenuControl.setMenuNum(MainMenu.MENU_NUM);
            System.out.println("Returned to main menu.");
        }
    }

    public static void setUserID(String userID) {
        EditMenu.userID = userID;
    }
}
