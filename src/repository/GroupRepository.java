package repository;

import entity.Group;
import entity.Message;
import input.ChatMenu;
import input.GroupEditMenu;
import input.MainMenu;
import input.MenuControl;
import jdbc.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupRepository {
    private static final Scanner scanner = new Scanner(System.in);

    public static void create_group(String ownerID) throws SQLException {
        String id = scan_groupID();
        if (id == null) return;
        String groupName = scan_groupName();
        if (groupName == null) return;

        if (Database.add_group(new Group(id, groupName, ownerID)) > 0 &&
        Database.join_group(ownerID, id)>1 && Database.add_admin(ownerID, id)>0)
            System.out.println("Group created successfully.");
        else System.err.println("Group creation failed.\nPlease try again.");
    }

    private static String scan_groupID() {
        System.out.print("Group ID:\n@");
        while (true) {
            try {
                String id = scanner.nextLine().trim();
                if (id.equalsIgnoreCase("cancel")) return null;
                if (safe_id(id)) return id;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean safe_id(String id) throws SQLException {
        if (id.isEmpty()) {
            System.err.println("id can't be empty.");
            return false;
        }

        Pattern pattern = Pattern.compile("[^\\w_]");
        Matcher matcher = pattern.matcher(id);
        if (matcher.find()) {
            System.err.println("Invalid character.");
            return false;
        }

        if (Database.group_exists(id)) {
            System.err.println("This id already exist.");
            return false;
        }

        return true;
    }

    private static String scan_groupName() {
        System.out.println("groupName:");
        while (true) {
            try {
                String groupName = scanner.nextLine().trim();
                if (groupName.equalsIgnoreCase("cancel")) return null;
                if (!groupName.isEmpty()) return groupName;
                else System.err.println("groupName can't be empty.");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void show_groups(String userID) throws SQLException {
        ResultSet resultSet = Database.joined_groups(userID);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
            System.out.println("----------------------");
        }
    }

    public static void show_chat(String userID) throws SQLException {
        System.out.println("Insert groupID:");
        while (true) {
            String groupID = scanner.nextLine().trim();
            if (Database.isJoined(userID, groupID)) {
                ChatMenu.setGroupID(groupID);
                ChatMenu.setUserID(userID);
                ChatMenu.setAdmin(Database.isAdmin(userID, groupID));
                ChatMenu.setOwner(Database.isOwner(userID,groupID));
                MenuControl.setMenuNum(ChatMenu.MENU_NUM);
                System.out.println("@"+groupID+"'s chat:");
                return;
            }
            else System.err.println("You're not joined in this group.");
        }
    }

    public static void show_group_info(String groupID) throws SQLException {
        System.out.println(Database.get_group(groupID));
    }

    public static void edit(String groupID) {
        System.out.println("EDIT");
        GroupEditMenu.setGroupID(groupID);
        MenuControl.setMenuNum(GroupEditMenu.MENU_NUM);
    }

    public static void return_to_chat() {
        GroupEditMenu.setGroupID(null);
        MenuControl.setMenuNum(ChatMenu.MENU_NUM);
    }

    public static void change_name(String groupID) throws SQLException {
        String newGroupName = scan_groupName();
        if (Database.update_groupName(groupID, newGroupName) > 0)
            System.out.println("groupName changed successfully.");
    }

    public static void change_biography(String groupID) throws SQLException {
        String biography = scan_biography();
        if (Database.update_groupBiography(groupID, biography) > 0)
            System.out.println("Biography changed successfully.");
    }

    private static String scan_biography() {
        System.out.println("biography(type end to finish):");
        StringBuilder bio = new StringBuilder();
        while (true) {
            String line = scanner.nextLine();
            if (line.trim().equalsIgnoreCase("end")) {
                return bio.toString();
            }
            else bio.append(line).append("\n");
        }
    }

    public static void show_members(String groupID) throws SQLException {
        ResultSet adminsTableSet = Database.get_adminsTable(groupID);
        System.out.println("admins:");
        while (adminsTableSet.next()) {
            System.out.println("@"+adminsTableSet.getString(1));
        }

        System.out.println("\nmembers:");
        ResultSet membersTableSet = Database.get_membersTable(groupID);
        while (membersTableSet.next()) {
            System.out.println("@"+membersTableSet.getString(1));
        }
        System.out.println("------------------------");
    }

    public static void add_member(String groupID) throws SQLException {
        System.out.println("new member's id:");
        while (true) {
            String userID = scanner.nextLine().trim();
            if (userID.equalsIgnoreCase("cancel")) return;
            if (Database.user_exists(userID)) {
                if (!Database.isJoined(userID,groupID)) {
                    if (Database.join_group(userID,groupID)>1) {
                        System.out.println("User joined successfully.");
                        return;
                    }
                    else System.err.println("Adding member failed.");
                }
                else System.err.println("This user is already joined.");
            }
            else System.err.println("User doesn't exist.");
        }
    }

    public static void add_admin(String groupID) throws SQLException {
        System.out.println("new admin 's id:");
        while (true) {
            String userID = scanner.nextLine().trim();
            if (userID.equalsIgnoreCase("cancel")) return;
            if (Database.user_exists(userID)) {
                if (Database.isJoined(userID,groupID)) {
                    if (!Database.isAdmin(userID,groupID)) {
                        if (Database.add_admin(userID,groupID)>0) {
                            System.out.println("Admin added successfully.");
                            return;
                        }
                        else System.err.println("Adding admin failed.");
                    }
                    else System.err.println("User is already admin.");
                }
                else System.err.println("Member doesn't exist.");
            }
            else System.err.println("User doesn't exist.");
        }
    }

    public static void main_menu() {
        ChatMenu.setGroupID(null);
        ChatMenu.setUserID(null);
        MenuControl.setMenuNum(MainMenu.MENU_NUM);
        System.out.println("MAIN MENU");
    }

    public static void leave(String userID, String groupID){
        try {
            if (Database.leave(userID, groupID) > 1) {
                System.out.println("Left the chat successfully.");
                main_menu();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove_admin(String groupID) throws SQLException {
        System.out.println("adminID:");
        while (true) {
            String adminID = scanner.nextLine().trim();
            if (adminID.equalsIgnoreCase("cancel")) return;
            if (Database.user_exists(adminID)) {
                if (Database.isAdmin(adminID,groupID)) {
                    if (Database.remove_admin(adminID,groupID) > 0) {
                        System.out.println("Admin removed successfully.");
                        return;
                    }
                    else System.err.println("Removing admin failed.");
                }
                else System.out.println("User is not admin.");
            }
            else System.err.println("User doesn't exist.");
        }
    }

    public static void kick_member(String groupID) throws SQLException {
        System.out.println("memberID:");
        while (true) {
            String memberID = scanner.nextLine().trim();
            if (memberID.equalsIgnoreCase("cancel")) return;
            if (Database.user_exists(memberID)) {
                if (Database.isJoined(memberID,groupID)) {
                    if (!Database.isAdmin(memberID,groupID)) {
                        if (Database.kick_member(memberID,groupID) > 0) {
                            System.out.println("Member kicked successfully.");
                            return;
                        }
                        else System.err.println("Couldn't kick user.");
                    }
                    else System.err.println("User is admin.");
                }
                else System.err.println("Member doesn't exist.");
            }
            else System.out.println("User doesn't exist.");
        }
    }

    public static void kick_admin(String groupID) throws SQLException {
        System.out.println("adminID:");
        while (true) {
            String adminID = scanner.nextLine().trim();
            if (adminID.equalsIgnoreCase("cancel")) return;
            if (Database.user_exists(adminID)) {
                if (Database.isJoined(adminID,groupID)) {
                    if (Database.isAdmin(adminID,groupID)) {
                        if (Database.kick_admin(adminID,groupID) > 0) {
                            System.out.println("admin kicked successfully.");
                            return;
                        }
                        else System.err.println("Couldn't kick user.");
                    }
                    else System.err.println("User is not admin.");
                }
                else System.err.println("Member doesn't exist.");
            }
            else System.out.println("User doesn't exist.");
        }
    }

    public static void send_message(String userID, String groupID) throws SQLException {
        System.out.println("""
                new
                reply
                """
        );
        String text;
        while (true) {
            String type = scanner.nextLine().trim();
            if (type.equalsIgnoreCase("cancel")) return;

            if (type.equalsIgnoreCase("new")) {
                text = MessageRepository.scan_text();
                if (text == null) return;
                break;
            }
            else if (type.equalsIgnoreCase("reply")) {
                text = scan_reply(groupID);
                break;
            }
        }

        if (Database.send_message_to_group(new Message(text,userID,groupID)) > 0)
            System.out.println("Message sent successfully.");
        else System.out.println("Sending message failed.");
    }

    private static String scan_reply(String groupID) {
        System.out.println("messageID:");
        while (true) {
            String messageID = scanner.nextLine().trim();
            if (messageID.equalsIgnoreCase("cancel")) return null;
            try {
                if (Database.relatedGroup(Integer.parseInt(messageID), groupID)) {
                    return "reply to message @" + messageID + ":\n" + MessageRepository.scan_text();
                } else System.err.println("Choose a related message.");
            }
            catch (Exception e) {
                System.err.println("Invalid input.");
            }
        }
    }

    public static void show_messages(String groupID) throws SQLException {
        ResultSet resultSet = Database.get_groupMessagesTable(groupID);
        while (resultSet.next()) {
            StringBuilder message = new StringBuilder();
            message.append("from @").append(resultSet.getString("senderID")).append(":\n");
            message.append(resultSet.getString("text")).append("\n");
            message.append(resultSet.getString("time")).append("\n");
            message.append("@").append(resultSet.getInt("id")).append("\n");
            message.append("------------------------------------------------");

            System.out.println(message);
        }
    }

    public static void destroy(String groupID) throws SQLException {
        main_menu();

        ResultSet resultSet = Database.get_membersTable(groupID);
        while (resultSet.next()) {
            Database.leave(resultSet.getString(1), groupID);
        }

        if (Database.destroy(groupID) >2) System.out.println("Group destroyed.");
        else System.out.println("Destroying failed.");
    }
}













