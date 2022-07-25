package repository;

import entity.Post;
import entity.Time;
import jdbc.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class PostRepository {
    private static final Scanner scanner = new Scanner(System.in);

    public static void create_post () throws SQLException {
        String caption = post_addText();
        String postID = post_postID();
        String posterID = post_posterID();
        boolean ad=post_ad();
        String picture=post_picture();
        String time=post_time();
        String field = post_field();

        System.out.println("Type POST to send the post. ");
        String sure = scanner.nextLine().trim();
        while (!sure.equalsIgnoreCase("POST")) {
            System.out.println("Please type POST!");
            sure = scanner.nextLine().trim();
        }

        if (Database.add_post(new Post( postID, posterID, ad,
                picture,caption,time,field)) > 0){
            System.out.println("Posted the post successfully.");
        }else System.err.println("Something went wrong!\n try again.");
    }

    public static void Delete_post(){
        System.out.println("Please enter post id:");
        while (true){
            try{
                String cap= scanner.nextLine().trim();
                if(cap.isEmpty()){
                    System.out.println("Dont enter empty String. dude?!");
                }else {
                    if(Database.delete_Post(cap)){
                        System.out.println("Deleted the post successfully.");
                    }else {
                        System.out.println("No post exists with that ID.");
                    }
                    return;
                }
            }catch (Exception e){
                System.out.println("Invalid command!");
            }
        }
    }

    public static void Show_Likes(){
        System.out.println("Is it a post or comment?");
        while (true) {
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("post")) {
                System.out.println("input the postID:");
                in = scanner.nextLine().trim();
                try {
                    Database.SHOW_LIKES_post(in);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            } else if (in.equalsIgnoreCase("comment")) {
                System.out.println("input the commentID:");
                in = scanner.nextLine().trim();
                try {
                    Database.SHOW_LIKES_comment(in);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public static void Show_10_Posts(){
        try {
            LikesRepository.create_likes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Show_Comments(){
        System.out.println("Is it a post or comment?");
        while (true) {
            String in = scanner.nextLine().trim();
            if (in.equalsIgnoreCase("post")) {
                System.out.println("input the postID:");
                in = scanner.nextLine().trim();
                try {
                    Database.SHOW_COMMENTS_post(in);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            } else if (in.equalsIgnoreCase("comment")) {
                System.out.println("input the commentID:");
                in = scanner.nextLine().trim();
                try {
                    Database.SHOW_COMMENTS_comment(in);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }

    public static void Update_Caption(){
        System.out.println("Please enter new caption:");
        while (true){
            try {
                String cap= scanner.nextLine();
                if(cap.isEmpty()){
                    System.out.println("Dont enter empty String. dude?!");
                }else {
                    String id = Database.user_loggedIn();
                    if(Database.Update_Caption(cap,id)>0){
                        System.out.println("Updated successfully.");
                        return;
                    }else {
                        System.out.println("No post exists with such ID.");
                    }

                }
            }catch (Exception e){
                System.out.println("Invalid command!");
            }
        }
    }

    //getting caption
    public static String post_addText(){
        System.out.println("Please enter the caption:");
        while (true){
            try {
                String caption=scanner.nextLine();
                if(caption.isEmpty()){
                    System.err.println("Caption can't be empty.");
                }else {
                    return caption;
                }
            }catch (Exception e){
                System.err.println("Invalid command.");
            }
        }
    }

    public static String post_postID() throws SQLException {
        int a = Database.get_postNum();
        String postID = post_posterID();
        if(Database.Update_postNum(Database.user_loggedIn(), Database.get_postNum())<=0){
            System.out.println("sth went wrong!");
        }
        return postID+a;
    }

    public static String post_posterID() throws SQLException {
        String user_loggedin = Database.user_loggedIn();
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

    public static boolean post_ad(){
        System.out.println("Is this an advertising post?(yes/no)");
        while (true){
            try {
                String answer =scanner.nextLine().trim();
                if(answer.equalsIgnoreCase("yes")){
                    if(Database.check_ads(post_posterID())){
                        return true;
                    }else {
                        System.out.println("This is not a business account!");
                        return false;
                    }
                }else if (answer.equalsIgnoreCase("no")){
                    return false;
                }
            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }

    // haven't checked for errors in path
    public static String post_picture (){
        System.out.println("Please enter picture path:");
        while (true){
            try {
                String path =scanner.nextLine();
                if(!path.isEmpty()){
                    return path;
                }else {
                    System.out.println("It's empty dude!");
                }
            }catch (Exception e){
                System.out.println("Invalid command");
            }
        }
    }

    public static String post_time (){
        return (new Time()).toString();
    }

    public static void Update_Location(){
        String new_location = post_location();
        while (true){
            try {
                String user_id= post_posterID();
                Database.Update_location(new_location,user_id);
                return;
            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }

    public static String post_location (){
        System.out.println("Give me your Location:");
        while (true){
            try {
                String Location = scanner.nextLine().trim();
                if(Location.contains("Iran")){
                    System.out.println("hamvatan salam.");
                    return Location;
                }
                return Location;
            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }

    public static String post_field (){
        System.out.println("Choose the post's field:(insert number)");
        System.out.println("""
                1.sports
                2.entertainment
                3.nature
                4.educational
                5.fashion
                6.political
                7.music
                8.movie
                9.economics
                """);
        while (true){
            try {
                String Location =scanner.next();
                switch (Location){
                    case "1": return "sports";
                    case "2": return "entertainment";
                    case "3": return "nature";
                    case "4": return "educational";
                    case "5": return "fashion";
                    case "6": return "political";
                    case "7": return "music";
                    case "8": return "movie";
                    case "9": return "economics";
                    default: System.out.println("invalid command!"); break;
                }

            }catch (Exception e){
                System.out.println("invalid command!");
            }
        }
    }
}
