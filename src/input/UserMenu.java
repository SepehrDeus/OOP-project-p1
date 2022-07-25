package input;

import entity.User;
import jdbc.Database;
import repository.MessageRepository;
import repository.UserRepository;

import java.sql.SQLException;

public class UserMenu {
    public static final int MENU_NUM = 2;
    private static String showedUserID;
    private static String watchingUserID;

    public static String getWatchingUserID() {
        return watchingUserID;
    }

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help")) {
            try {
                if (Database.isFollowed(watchingUserID, showedUserID)) {
                    System.out.println("User: " + showedUserID);
                    System.out.println("""
                            * unfollow
                            * show followers
                            * show following
                            * show posts
                            * send message
                            * main menu

                            help
                            exit
                            """
                    );
                }
                else {
                    System.out.println("User: " + showedUserID);
                    System.out.println("""
                            * follow
                            * show posts
                            * show followers
                            * show following
                            * send message
                            * main menu

                            help
                            exit
                            """
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

            // exit
        else if (input.equalsIgnoreCase("exit")){
            MenuControl.setRun(false);
        }

        // follow
        else if (input.equalsIgnoreCase("follow")) {
            try {
                if (!Database.isFollowed(watchingUserID, showedUserID)) {
                    UserRepository.follow(watchingUserID, showedUserID);
                }
                else System.out.println("This user is already followed!");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        // unfollow
        else if (input.equalsIgnoreCase("unfollow")) {
            try {
                if (Database.isFollowed(watchingUserID, showedUserID)) {
                    UserRepository.unfollow(watchingUserID, showedUserID);
                }
                else System.out.println("This user is not followed!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show followers
        else if (input.equalsIgnoreCase("show followers")) {
            try {
                UserRepository.show_followers(showedUserID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show following
        else if (input.equalsIgnoreCase("show following")) {
            try {
                UserRepository.show_following(showedUserID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show posts of followers and followings and user himself
        else if (input.equalsIgnoreCase("show posts")) {
            UserRepository.show_posts();
        }

        // send message
        else if (input.equalsIgnoreCase("send message")) {
            try {
                MessageRepository.send_message(watchingUserID, showedUserID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // main menu
        else if (input.equalsIgnoreCase("main menu")) {
            setShowedUserID(null);
            MenuControl.setMenuNum(MainMenu.MENU_NUM);
            System.out.println("Returned to main menu.");
        }
    }

    public static void setShowedUserID(String showedUserID) {
        UserMenu.showedUserID = showedUserID;
    }

    public static void setWatchingUserID(String watchingUserID) {
        UserMenu.watchingUserID = watchingUserID;
    }
}
