package entity;

import jdbc.Database;

import java.util.ArrayList;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean business; // 1 = true, 0 = false
    private String biography;
    private String email;
    private String website;
    private String followersTableName; // id+"FollowersTable"
    private int followersNum;
    private String followingTableName; // id+"FollowingTable"
    private int followingNum;
    private int postsNum;
    private String groupsTableName; // id+"GroupsTable"
    private String Logged_in;

    public User(String id, String username, String password, boolean business) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.business = business;
        followersNum = followingNum = postsNum = 0;
        followersTableName = Database.create_followersTable(id);
        followingTableName = Database.create_followingTable(id);
        groupsTableName = Database.create_groupsTable(id);
        this.Logged_in="no";
    }

    public User() {}

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFollowersTableName() {
        return followersTableName;
    }

    public String getFollowingTableName() {
        return followingTableName;
    }

    public String getGroupsTableName() {
        return groupsTableName;
    }

    public int isBusiness() {
        return business ? 1 : 0;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setFollowersNum(int followersNum) {
        this.followersNum = followersNum;
    }

    public void setFollowingNum(int followingNum) {
        this.followingNum = followingNum;
    }

    public void setPostsNum(int postsNum) {
        this.postsNum = postsNum;
    }

    public String isLogged_in() {
        return Logged_in;
    }

    public void setLogged_in(String logged_in) {
        Logged_in = logged_in;
    }

    @Override
    public String toString() {
        return "id: "+id+"\n" +
                "username: "+username+"\n" +
                "biography: "+biography+"\n" +
                "email: "+email+"\n" +
                "website: "+website+"\n" +
                "followers: "+followersNum+"\n" +
                "following: "+followingNum+"\n" +
                "posts: "+postsNum+"\n";
    }
}
