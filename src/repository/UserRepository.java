package repository;

import input.EditMenu;
import input.UserMenu;
import jdbc.Database;
import entity.User;
import input.MainMenu;
import input.MenuControl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRepository {
    private static final Scanner scanner = new Scanner(System.in);

    public static void register() throws SQLException {
        System.out.println("Enter <cancel> for cansel.");
        String id = register_id();
        if (id==null) return;
        String username = register_username();
        if (username==null) return;
        String password = register_password();
        if (password==null) return;
        boolean business = register_business();

        if (Database.add_user(new User(id, username, password, business)) > 0)
            System.out.println("Registered successful.");
        else System.err.println("Something went wrong!\nPlease try again.");
    }

    public static void show_posts(){
        String id=UserMenu.getWatchingUserID();
        try {
            ResultSet resultSet1 = Database.get_followingTable(Database.FollowingsID(id));
            ResultSet resultSet2 = Database.get_followersTable(Database.FollowersID(id));
            Database.show_posts(id,resultSet1,resultSet2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static String register_id() {
        System.out.print("Insert your id (only alphanumerical characters and underline):\n@");
        while (true) {
            try {
                String id = scanner.nextLine().trim();
                if (id.equalsIgnoreCase("cancel")) return null;
                if (safe_id(id)) return id;
            }
            catch (Exception e) {
                System.err.println("invalid command.");
            }
        }
    }

    private static boolean safe_id(String id) throws SQLException {
        if (id.isEmpty()){
            System.err.println("id can't be empty.");
            return false;
        }

        Pattern pattern = Pattern.compile("[^\\w_]");
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            System.err.println("Invalid character.");
            return false;
        }

        if (Database.user_exists(id)) {
            System.err.println("This id already exist.");
            return false;
        }

        return true;
    }

    private static String register_username() {
        System.out.println("Insert your username: ");
        while (true) {
            try {
                String username = scanner.nextLine().trim();
                if (username.equalsIgnoreCase("cancel")) return null;
                if (!username.isEmpty()) return username;
                else System.err.println("username can't be empty.");
            }
            catch (Exception e) {
                System.err.println("invalid command.");
            }
        }
    }

    private static String register_password() {
        System.out.println("insert yor password (at least 8 character, including both numbers and capital letters): ");
        while (true) {
            try {
                String password = scanner.nextLine().trim();
                if (password.equalsIgnoreCase("cancel")) return null;
                if (safe_password(password)) return password;
            }
            catch (Exception e) {
                System.err.println("invalid command.");
            }
        }
    }

    private static boolean safe_password(String pass) {
        if (pass.isEmpty()) {
            System.err.println("password can't be empty.");
            return false;
        }

        if (pass.length() < 8) {
            System.err.println("password must have at least 8 characters.");
            return false;
        }

        Pattern pattern1 = Pattern.compile("[A-Z]");
        Pattern pattern2 = Pattern.compile("[a-z]");
        Pattern pattern3 = Pattern.compile("[0-9]");
        Matcher matcher1 = pattern1.matcher(pass);
        Matcher matcher2 = pattern2.matcher(pass);
        Matcher matcher3 = pattern3.matcher(pass);
        if (!matcher1.find() || !matcher2.find() || !matcher3.find()) {
            System.err.println("password must consist small and capital letters and numbers.");
            return false;
        }

        return true;
    }

    private static boolean register_business() {
        System.out.println("Is this a business account? (yes or no)");
        while (true) {
            try {
                String str = scanner.nextLine().trim();
                if (str.equalsIgnoreCase("yes")) return true;
                else if (str.equalsIgnoreCase("no")) return false;
                else System.out.println("Invalid input.");
            }
            catch (Exception e) {
                System.err.println("invalid command.");
            }
        }
    }



    public static void login () throws SQLException {
        if (Database.number_of_users() == 0) {
            System.out.println("No user has registered yet.");
            return;
        }
        try {
            System.out.println("Enter <cancel> for cancel.");
            String id = login_id();
            if (id==null) return;
            if (login_password(id)) {
                System.out.println("Logged in successfully.\n" +
                        "WELCOME\n");
                MainMenu.setUserID(id);
                MenuControl.setMenuNum(1);
            }
        }
        catch (Exception e) {
            System.out.println("Something went wrong!\ntry again.");
        }
    }

    private static String login_id() {
        System.out.println("id: ");
        while (true) {
            try {
                String id = scanner.nextLine().trim();
                if (id.equalsIgnoreCase("cancel")) return null;
                if (Database.user_exists(id)) {
                    if(Database.Update_logged_in_yes(id)>0){
                        return id;
                    }
                    else {
                        System.out.println("could not update index!");
                    }
                }
                else System.err.println("user doesn't exist.");
            }
            catch (Exception e) {
                System.err.println("Invalid command.");
            }
        }
    }

    private static boolean login_password(String id) {
        System.out.println("password: ");
        while (true) {
            try {
                String password = scanner.nextLine().trim();
                if (Database.check_password(id, password)){
                    System.out.println("Correct password.");
                    return true;
                }
                else System.err.println("wrong password!");
            }
            catch (Exception e) {
                System.err.println("Invalid command.");
            }
        }
    }

    public static void logout() {
        String id = null;
        try {
            id= Database.user_loggedIn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Database.Update_logged_in_no(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Logged out successfully.");
        MainMenu.setUserID(null);
        MenuControl.setMenuNum(0);
    }

    public static void delete_user(String id) throws SQLException {
        System.out.println("password: ");
        while (true) {
            try {
                String password = scanner.nextLine().trim();
                if (Database.check_password(id, password)) {
                    System.out.println("Correct password.");
                    break;
                }
                else System.err.println("wrong password!");
            }
            catch (Exception e) {
                System.err.println("Invalid command.");
            }
        }

        System.out.println("Are you sure? (yes or no)");
        while (true) {
            String answer = scanner.nextLine().trim();
            if (answer.equalsIgnoreCase("no")) return;
            else if (answer.equalsIgnoreCase("yes")) {
                logout();
                Database.delete_user(id);
                System.out.println("deleted successfully.");
                return;
            }
            else System.out.println("Invalid input.");
        }
    }

    public static void show_user(String userID) {
        System.out.println("Please insert userID:");
        while (true) {
            try {
                String id = scanner.nextLine().trim();
                if (id.equalsIgnoreCase("cancel")) return;
                if (Database.user_exists(id)){
                    System.out.println("User found.");
                    UserMenu.setShowedUserID(id);
                    UserMenu.setWatchingUserID(userID);
                    MenuControl.setMenuNum(UserMenu.MENU_NUM);
                    show_user_info(id);
                    return;
                }
                else System.err.println("user doesn't exist.");
            }
            catch (Exception e) {
                System.err.println("Invalid command.");
            }
        }
    }




    public static void edit(String id) {
        System.out.println("password: ");
        while (true) {
            try {
                String password = scanner.nextLine().trim();
                if (Database.check_password(id, password)) {
                    System.out.println("Correct password.");
                    EditMenu.setUserID(id);
                    MenuControl.setMenuNum(EditMenu.MENU_NUM);
                    return;
                }
                else System.err.println("wrong password!");
            }
            catch (Exception e) {
                System.err.println("Invalid command.");
            }
        }
    }

    public static void show_user_info(String id) throws SQLException {
        User user = Database.get_user_by_id(id);
        System.out.println(user);
    }

    public static void update_username(String id) throws SQLException {
        System.out.println("new username: ");
        while (true) {
            try {
                String username = scanner.nextLine().trim();
                if (username.equalsIgnoreCase("cancel")) return;
                if (!username.isEmpty()) {
                    if (Database.update_username(id, username)>0) {
                        System.out.println("Username changed successfully.");
                        return;
                    }
                }
                else System.err.println("username can't be empty.");
            }
            catch (Exception e) {
                System.err.println("invalid command.");
            }
        }
    }

    public static void update_password(String id) throws SQLException {
        System.out.println("new password:");
        while (true) {
            try {
                String password = scanner.nextLine().trim();
                if (password.equalsIgnoreCase("cancel")) return;
                if (safe_password(password) && Database.update_password(id, password)>0) {
                    System.out.println("Password changed successfully.");
                    return;
                }
            }
            catch (Exception e) {
                System.err.println("invalid command.");
            }
        }
    }

    public static void update_biography(String id) throws SQLException {
        System.out.println("new biography(type end to finish):");
        StringBuilder bio = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("cancel")) return;
            else if (line.trim().equalsIgnoreCase("end")) break;
            else bio.append(line).append("\n");
        }

        if (Database.update_biography(id, bio.toString()) > 0) {
            System.out.println("Changed successfully.");
        }
    }

    public static void update_email(String id) throws SQLException {
        System.out.println("new email:");
        while (true) {
            String str = scanner.nextLine().trim();
            if (str.equalsIgnoreCase("cancel")) return;
            if (str.isEmpty()) System.out.println("Field shouldn't be empty.");
            else if (Database.update_email(id, str) > 0) {
                System.out.println("Changed successfully.");
                return;
            }
        }
    }

    public static void update_website(String id) throws SQLException {
        System.out.println("new website:");
        while (true) {
            String str = scanner.nextLine().trim();
            if (str.equalsIgnoreCase("cancel")) return;
            if (str.isEmpty()) System.out.println("Field shouldn't be empty.");
            else if (Database.update_website(id, str) > 0) {
                System.out.println("Changed successfully.");
                return;
            }
        }
    }

    public static void follow(String followerID, String followingID) {
        try {
            if (Database.follow(followerID, followingID)) {
                System.out.println("Followed.");
            }
            else System.err.println("Error!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unfollow(String followerID, String followingID) {
        try {
            if (Database.unfollow(followerID, followingID)) {
                System.out.println("Unfollowed");
            }
            else System.err.println("Error!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  static void show_followers(String userID) throws SQLException {
        System.out.println("followers:");
        ResultSet resultSet = Database.get_followersTable(userID);
        while (resultSet.next()) {
            System.out.println("@"+resultSet.getString(1));
        }
        System.out.println("----------------------------------");
    }

    public static void show_following(String userID) throws SQLException {
        System.out.println("following:");
        ResultSet resultSet = Database.get_followingTable(userID);
        while (resultSet.next()) {
            System.out.println("@"+resultSet.getString(1));
        }
        System.out.println("----------------------------------");
    }
}








