package jdbc;

import java.sql.*;

public class JDBC {
    private static Connection connection;

    public static boolean run_jdbc() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/deus",
                    "root",
                    "19283746"
            );

            JDBC.connection = connection;

            Database.setConnection(connection);

            create_tables(connection);
            create_posts(connection);
            create_Comments(connection);
            create_Likes(connection);

            System.out.println("Connected successfully.");

            return true;
        }
        catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    private static void create_tables(Connection connection) throws SQLException {
        initUsersTable(connection.createStatement());
        initMessagesTable(connection.createStatement());
        initGroupsAllTable(connection.createStatement());

    }

    private static void initUsersTable(Statement statement) throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+
                        "users(id varchar(255) NOT NULL, " +
                        "username varchar(255) NOT NULL, " +
                        "password varchar(255) NOT NULL, " +
                        "business int NOT NULL, " +
                        "biography varchar(500), " +
                        "email varchar(255), " +
                        "website varchar(255)," +
                        "followersTableName varchar (300) NOT NULL , " +
                        "followersNum int NOT NULL , " +
                        "followingTableName varchar (300) NOT NULL , " +
                        "followingNum int NOT NULL , " +
                        "postsNum int NOT NULL , " +
                        "groupsTableName varchar (300) NOT NULL , " +
                        "Logged_in varchar(10) NOT NULL, " +
                        "PRIMARY KEY (id))"
        );

        statement.close();
    }

    private static void initMessagesTable(Statement statement) throws SQLException { // private message
        statement.execute(
                "CREATE TABLE IF NOT EXISTS messages(" +
                        "id int NOT NULL AUTO_INCREMENT, " +
                        "text varchar(500) NOT NULL, " +
                        "time varchar(100) NOT NULL, " +
                        "senderID varchar(255) NOT NULL, " +
                        "receiverID varchar(255) NOT NULL, " +
                        "PRIMARY KEY (id))"
        );

        statement.close();
    }

    public static void initFollowersTable(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+name+" (followersID varchar (255), UNIQUE (followersID))"
        );
        statement.close();
    }

    public static void initFollowingTable(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+name+" (followingID varchar (255), UNIQUE (followingID))"
        );
        statement.close();
    }

    private static void initGroupsAllTable(Statement statement) throws SQLException { // all groups information
        statement.execute(
                "CREATE TABLE IF NOT EXISTS groupsAll(" +
                        "id varchar (255) NOT NULL, " +
                        "groupName varchar (255) NOT NULL, " +
                        "biography varchar (500), " +
                        "ownerID varchar (255) NOT NULL, " +
                        "membersTableName varchar (300) NOT NULL ," +
                        "adminsTableName varchar (300) NOT NULL, " +
                        "messagesTableName varchar (300) NOT NULL, " +
                        "PRIMARY KEY (id))"
        );

        statement.close();
    }

    public static void initGroupsTable(String name) throws SQLException { // groups of each user
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+name+" (groupsID varchar (255), UNIQUE (groupsID))"
        );
        statement.close();
    }

    public static void initMembersTable(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+name+" (membersID varchar (255), UNIQUE (membersID))"
        );
        statement.close();
    }

    public static void initAdminsTable(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+name+" (adminsID varchar (255), UNIQUE (adminsID))"
        );
        statement.close();
    }

    public static void initGroupMessagesTable(String name) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+name+" (" +
                        "id int NOT NULL AUTO_INCREMENT, " +
                        "text varchar(500) NOT NULL, " +
                        "time varchar(100) NOT NULL, " +
                        "senderID varchar(255) NOT NULL, " +
                        "PRIMARY KEY (id))"
        );
        statement.close();
    }

    //posts connection to database and creating tables
    private static void initPostTable(Statement statement) throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+
                        "posts(id varchar(255) NOT NULL, "+
                        "posterid varchar(255) NOT NULL, " +
                        "ad int NOT NULL, " +
                        "caption varchar(255) NOT NULL, " +
                        "pictureid varchar(255) NOT NULL,"+
                        "likesNum int NOT NULL, "+
                        "commentsNum int NOT NULL, "+
                        "Timy varchar(255) NOT NULL, "+
                        "Location varchar(255), "+
                        "field varchar(255) NOT NULL, "+
                        "viewsNum int NOT NULL, "+
                        "PRIMARY KEY (id))"
        );

        statement.close();
    }
    private static void create_posts(Connection connection) throws SQLException {
        initPostTable(connection.createStatement());
    }


    //comments connection to database and creating tables
    private static void initCommentTable(Statement statement) throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+
                        "comments(postORcommentID varchar(255) NOT NULL, "+
                        "id varchar(255) NOT NULL, "+
                        "commenterid varchar(255) NOT NULL, "+
                        "comment_caption varchar(255) NOT NULL, "+
                        "likesNum int NOT NULL, "+
                        "commentsNum int NOT NULL, "+
                        "timy varchar(255) NOT NULL, "+
                        "PRIMARY KEY (id) )"
        );

        statement.close();
    }
    private static void create_Comments(Connection connection) throws SQLException {
        initCommentTable(connection.createStatement());
    }


    //Likes connection to database and creating tables
    private static void initLikesTable(Statement statement) throws SQLException {
        statement.execute(
                "CREATE TABLE IF NOT EXISTS "+
                        "Likes(post_OR_comment_ID varchar(255) NOT NULL, "+
                        "Like_ID varchar(255) NOT NULL, "+
                        "Liker_ID varchar(255) NOT NULL, " +
                        "PRIMARY KEY (Like_ID) )"
        );
        statement.close();
    }
    private static void create_Likes(Connection connection) throws SQLException {
        initLikesTable(connection.createStatement());
    }

    public static Connection getConnection() {
        return connection;
    }
}
