package input;

import repository.GroupRepository;

import java.sql.SQLException;

public class ChatMenu {
    public static final int MENU_NUM = 4;
    private static String groupID;
    private static String userID;
    private static boolean admin;
    private static boolean owner;

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help")) {
            if (admin && owner) {
                System.out.println("""
                        * show messages
                        * send message
                        * show group info
                        * edit
                        * show members
                        * add member
                        * add admin
                        * remove admin
                        * kick member
                        * kick admin
                        * destroy
                        - main menu
                        
                        help
                        exit
                        """);
            }
            else if (admin) System.out.println("""
                        * show messages
                        * send message
                        * show group info
                        * edit
                        * show members
                        * add member
                        * add admin
                        * kick member
                        * leave
                        - main menu
                        
                        help
                        exit
                        """);
            else System.out.println("""
                        * show messages
                        * send message
                        * show group info
                        * show members
                        * leave
                        - main menu
                        
                        help
                        exit
                        """);
        }

        // exit
        else if (input.equalsIgnoreCase("exit")) {
            MenuControl.setRun(false);
        }

        // show messages
        else if (input.equalsIgnoreCase("show messages")) {
            try {
                GroupRepository.show_messages(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // send message
        else if (input.equalsIgnoreCase("send message")) {
            try {
                GroupRepository.send_message(userID,groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show group info
        else if (input.equalsIgnoreCase("show group info")) {
            try {
                GroupRepository.show_group_info(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // show members
        else if (input.equalsIgnoreCase("show members")) {
            try {
                GroupRepository.show_members(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // leave
        else if (input.equalsIgnoreCase("leave") && !owner) {
            GroupRepository.leave(userID,groupID);
        }

        // main menu
        else if (input.equalsIgnoreCase("main menu")) {
            GroupRepository.main_menu();
        }

        // edit
        else if (input.equalsIgnoreCase("edit") && admin) {
            GroupRepository.edit(groupID);
        }

        // add member
        else if (input.equalsIgnoreCase("add member") && admin) {
            try {
                GroupRepository.add_member(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // add admin
        else if (input.equalsIgnoreCase("add admin") && admin) {
            try {
                GroupRepository.add_admin(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // remove admin
        else if (input.equalsIgnoreCase("remove admin") && owner) {
            try {
                GroupRepository.remove_admin(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // kick member
        else if (input.equalsIgnoreCase("kick member") && admin) {
            try {
                GroupRepository.kick_member(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // kick admin
        else if (input.equalsIgnoreCase("kick admin") && owner) {
            try {
                GroupRepository.kick_admin(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // destroy
        else if (input.equalsIgnoreCase("destroy") && owner) {
            try {
                GroupRepository.destroy(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setGroupID(String groupID) {
        ChatMenu.groupID = groupID;
    }

    public static void setUserID(String userID) {
        ChatMenu.userID = userID;
    }

    public static void setAdmin(boolean admin) {
        ChatMenu.admin = admin;
    }

    public static void setOwner(boolean owner) {
        ChatMenu.owner = owner;
    }
}
