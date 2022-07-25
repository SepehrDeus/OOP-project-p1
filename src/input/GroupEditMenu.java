package input;

import repository.GroupRepository;

import java.sql.SQLException;

public class GroupEditMenu {
    public static final int MENU_NUM = 5;
    private static String groupID;

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help")) {
            System.out.println("""
                    * change name
                    * change biography
                    * return
                    
                    help
                    exit
                    """
            );
        }

        // exit
        else if (input.equalsIgnoreCase("exit")) {
            MenuControl.setRun(false);
        }

        // change name
        else if (input.equalsIgnoreCase("change name")) {
            try {
                GroupRepository.change_name(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // change biography
        else if (input.equalsIgnoreCase("change biography")) {
            try {
                GroupRepository.change_biography(groupID);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // return
        else if (input.equalsIgnoreCase("return")) {
            GroupRepository.return_to_chat();
        }
    }

    public static void setGroupID(String groupID) {
        GroupEditMenu.groupID = groupID;
    }
}
