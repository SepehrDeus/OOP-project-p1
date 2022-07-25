package input;

import repository.PostRepository;

import java.sql.SQLException;

public class CreatePostMenu {
    public static final int MENU_NUM = 6;

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help"))
            System.out.println("""
                    POST MENU:
                    * ADD TEXT
                    * ADD LOCATION
                    help
                    return
                    exit
                    """
            );

        // exit
        else if (input.equalsIgnoreCase("exit")){
            MenuControl.setRun(false);
        }

        // return
        else if (input.equalsIgnoreCase("return")){
            MenuControl.setMenuNum(MainMenu.MENU_NUM);
        }

        // ad text
        else if (input.equalsIgnoreCase("add text")) {
            try {
                PostRepository.create_post();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // add location
        else if (input.equalsIgnoreCase("add location")) {
            PostRepository.Update_Location();
        }
    }
}
