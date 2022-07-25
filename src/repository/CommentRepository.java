package repository;

import entity.Comment;
import entity.Time;
import jdbc.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class CommentRepository {
    private static final Scanner scanner = new Scanner(System.in);

    public static void create_comment () throws SQLException {
        String post_comment_id = comment_OR_post_ID();
        int a;
        if(comment_OR_post_ID().charAt(post_comment_id.length()-1)=='#'){
            a = Database.get_commentNum_from_comments(post_comment_id.substring(0,post_comment_id.length()-1))+1;
            Database.Update_commentNum_from_comments(post_comment_id.substring(0,post_comment_id.length()-1),a);
        }
        else if(comment_OR_post_ID().charAt(post_comment_id.length()-1)=='*'){
            a = Database.get_commentNum_from_posts(post_comment_id.substring(0,post_comment_id.length()-1))+1;
            Database.Update_commentNum_from_posts(post_comment_id.substring(0,post_comment_id.length()-1),a);
        }

        String commentID = comment_comment_ID();
        String commenterID = comment_commenter_ID();
        String time=comment_time();
        String caption = comment_addText();


        if (Database.add_comment(new Comment(post_comment_id,commentID,commenterID,caption,time)) > 0){
            System.out.println("commented successful.");
        }else System.err.println("Something went wrong!\n try again.");
    }

    public static void Delete_comment(){
        System.out.println("please enter comment id :");
        while (true){
            try{
                String cap= scanner.nextLine().trim();
                if(cap.isEmpty()){
                    System.out.println("Don't enter empty String. dude?!");
                }else {
                    if(Database.delete_comment(cap)){
                        System.out.println("Deleted the post successfully.");
                    }else {
                        System.out.println("No post exists with that ID.");
                    }
                    return;
                }
            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }

    public static void Update_Comment(){
        System.out.println("please enter new caption:");
        while (true){
            try {
                String cap= scanner.nextLine();
                if(cap.isEmpty()){
                    System.out.println("dont enter empty String . dude?!");
                }else {
                    String id=Database.user_loggedIn();
                    if(Database.Update_comment(cap,id)>0){
                        System.out.println("Updated successfully.");
                        return;
                    }else {
                        System.out.println("No post exists with such ID.");
                    }
                    Database.Update_comment(cap,id);
                    return;
                }
            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }

    //getting data from user
    public static String comment_commenter_ID() throws SQLException{
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

    public static String comment_comment_ID() throws SQLException {
        String commenter=comment_commenter_ID();
        String post_comment_ID=comment_OR_post_ID();
        return post_comment_ID + commenter ;
    }

    public static String comment_addText(){
        System.out.println("Please enter the caption:");
        while (true){
            try {
                String caption=scanner.nextLine();
                if(caption.isEmpty()){
                    System.err.println("caption can't be empty.");
                }else {
                    return caption;
                }
            }catch (Exception e){
                System.err.println("invalid command.");
            }
        }
    }

    //the one we are leaving comment for
    public static String comment_OR_post_ID (){
        System.out.println("Please enter the id of the post or comment:");
        while (true){
            try {
                String caption= scanner.nextLine().trim();
                if(caption.isEmpty()){
                    System.err.println("id can't be empty.");
                }
                else {
                    System.out.println("is it a commentID or a postID?(comment/post)");
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

    public static String comment_time (){
        return (new Time()).toString();
    }
}
