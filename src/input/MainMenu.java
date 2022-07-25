package input;

import repository.GroupRepository;
import repository.MessageRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.sql.SQLException;

public class MainMenu {
    public static final int MENU_NUM = 1;
    private static String userID;

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help"))
            System.out.println("""
                    MAIN MENU
                    * home page
                    * create post
                    * delete post
                    * update post caption
                    * message inbox
                    * send message
                    * sent messages
                    * show groups
                    * show chat
                    * create group
                    * show user (search user)
                    * edit
                    * logout
                    * delete account

                    help
                    exit
                    """
            );

        // exit
        else if (input.equalsIgnoreCase("exit")){
            MenuControl.setRun(false);
        }

        // home page
        else if (input.equalsIgnoreCase("home page")) {
            MenuControl.setMenuNum(HomePage.MENU_NUM);
        }

        // create post
        else if (input.equalsIgnoreCase("create post")){
            MenuControl.setMenuNum(CreatePostMenu.MENU_NUM);
        }

        // delete post
        else if (input.equalsIgnoreCase("DELETE POST")){
            PostRepository.Delete_post();
        }

        // update post caption
        else if (input.equalsIgnoreCase("UPDATE POST CAPTION")){
            PostRepository.Update_Caption();
        }

        // message inbox
        else if (input.equalsIgnoreCase("message inbox")) {
            try {
                MessageRepository.message_inbox(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // send message
        else if (input.equalsIgnoreCase("send message")) {
            try {
                MessageRepository.send_message(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // sent
        else if (input.equalsIgnoreCase("sent messages")) {
            try {
                MessageRepository.sent(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show groups
        else if (input.equalsIgnoreCase("show groups")) {
            try {
                GroupRepository.show_groups(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show chat
        else if (input.equalsIgnoreCase("show chat")) {
            try {
                GroupRepository.show_chat(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // create group
        else if (input.equalsIgnoreCase("create group")) {
            try {
                GroupRepository.create_group(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show user
        else if (input.equalsIgnoreCase("show user") || input.equalsIgnoreCase("search user")) {
            UserRepository.show_user(userID);
        }

        // edit
        else if (input.equalsIgnoreCase("edit")) {
            UserRepository.edit(userID);
        }

        // logout
        else if (input.equalsIgnoreCase("logout")) {
            UserRepository.logout();
        }

        // delete account
        else if (input.equalsIgnoreCase("delete account")) {
            try {
                UserRepository.delete_user(userID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setUserID(String userID) {
        MainMenu.userID = userID;
    }
}
