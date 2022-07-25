package input;

import jdbc.Database;
import repository.CommentRepository;
import repository.LikesRepository;
import repository.PostRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class HomePage {
    public static final int MENU_NUM = 7;
    private static final Scanner scanner = new Scanner(System.in);

    public static void input_processor(String input) {
        // help
        if (input.equalsIgnoreCase("help"))
            System.out.println("""
                    HOME PAGE:
                    * LATEST 10 POSTS
                    * LIKE A POST
                    * DELETE LIKING OF A POST
                    * LEAVE A COMMENT
                    * UPDATE A COMMENT
                    * DELETE A COMMENT
                    * SHOW STATS
                    * SHOW COMMENTS
                    * SHOW LIKES
                    * RETURN MAIN MENU
                    help
                    exit
                    """
            );

        // exit
        else if (input.equalsIgnoreCase("exit")){
            MenuControl.setRun(false);
        }

        else if (input.equalsIgnoreCase("return")){
            MenuControl.setMenuNum(MainMenu.MENU_NUM);
        }

        // LIKE A POST
        else if (input.equalsIgnoreCase("LIKE A POST")){
            PostRepository.Show_10_Posts();
        }

        // LATEST 10 POSTS
        else if (input.equalsIgnoreCase("LATEST 10 POSTS")){
            try {
                Database.SHOW_LATEST_posts();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // DELETE LIKING OF A POST
        else if (input.equalsIgnoreCase("DELETE LIKING OF A POST")){
            LikesRepository.Delete_Like();
        }

        // SHOW STATS
        else if (input.equalsIgnoreCase("SHOW STATS")){
            System.out.println("Enter your postID:");
            String unput = scanner.nextLine().trim();
            try {
                if(Database.check_ads_post(unput)){
                    Database.SHOW_VIEWS_post(unput);
                    Database.SHOW_LIKES_post(unput);
                }else {
                    System.out.println("It is not an advertising post!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if (input.equalsIgnoreCase("SHOW COMMENTS")){
            PostRepository.Show_Comments();
        }


        else if (input.equalsIgnoreCase("SHOW LIKES")){
            PostRepository.Show_Likes();
        }


        else if (input.equalsIgnoreCase("LEAVE A COMMENT")) {
            try {
                CommentRepository.create_comment();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        else if (input.equalsIgnoreCase("DELETE A COMMENT")) {
            CommentRepository.Delete_comment();
        }

        else if (input.equalsIgnoreCase("UPDATE A COMMENT")) {
            CommentRepository.Update_Comment();
        }
    }
}
