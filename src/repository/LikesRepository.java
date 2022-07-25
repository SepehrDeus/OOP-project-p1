package repository;

import entity.Like;
import jdbc.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class LikesRepository {
    private static final Scanner scanner = new Scanner(System.in);

    public static void create_likes () throws SQLException {
        String post_comment_id = Like_OR_post_ID();
        int a;
        if(post_comment_id.charAt(post_comment_id.length()-1)=='#'){
            a = Database.get_likesNum_from_comments(post_comment_id.substring(0,post_comment_id.length()-1))+1;
            Database.Update_likesNum_into_comments(a,post_comment_id.substring(0,post_comment_id.length()-1));
        }else if(post_comment_id.charAt(post_comment_id.length()-1)=='*'){
            a = Database.get_likesNum_from_posts(post_comment_id.substring(0,post_comment_id.length()-1))+1;
            Database.Update_likesNum_into_posts(a,post_comment_id.substring(0,post_comment_id.length()-1));
        }

        String LikeID = Like_ID();
        String LikerID = liker_ID();

        if (Database.add_likes(new Like(post_comment_id,LikeID,LikerID)) > 0){
            System.out.println("commented successful.");
        }else System.err.println("Something went wrong!\n try again.");
    }

    public static void Delete_Like (){
        System.out.println("please enter like id :");
        while (true){
            try{
                String cap= scanner.nextLine().trim();
                if(cap.isEmpty()){
                    System.out.println("Don't enter empty String. dude?!");
                }else {
                    if(Database.delete_like(cap)){
                        System.out.println("Like deleted.");
                    }else {
                        System.out.println("It wasn't liked by you ");
                    }
                    return;
                }
            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }

    public static String Like_OR_post_ID (){
        System.out.println("Please enter the id of the post or comment:");
        while (true){
            try {
                String caption= scanner.nextLine().trim();
                if(caption.isEmpty()){
                    System.err.println("id can't be empty.");
                }else {
                    System.out.println("Is it a commentID or a post ID?(comment/post)");
                    caption= scanner.nextLine().trim();
                    if(caption.equalsIgnoreCase("comment")){
                        return caption+"#";
                    }else if (caption.equalsIgnoreCase("post")){
                        return caption+"*";
                    }
                }
            }catch (Exception e){
                System.err.println("invalid command.");
            }
        }
    }

    public static String liker_ID() throws SQLException{
        String user_loggedin =Database.user_loggedIn();
        while (true){
            try {
                if(user_loggedin.isEmpty()){
                    System.out.println("No body is loggedIn!");
                }else {
                    return user_loggedin;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String Like_ID() throws SQLException {
        String Liker= liker_ID();
        String like_ID = Like_OR_post_ID ();
        return like_ID+Liker;
    }
}
