package entity;

import jdbc.Database;

import java.util.PrimitiveIterator;

public class Group {
    private String id;
    private String groupName;
    private String biography;
    private String ownerID;
    private String membersTableName; // id + "MembersTable"
    private String adminsTableName; // id + "AdminsTable"
    private String messagesTableName; // id + "MessagesTable"

    public Group(String id, String groupName, String ownerID) {
        this.id = id;
        this.groupName = groupName;
        this.ownerID = ownerID;
        membersTableName = Database.create_membersTable(id);
        adminsTableName = Database.create_adminsTable(id);
        messagesTableName = Database.create_messagesTable(id);
    }

    public String getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getMembersTableName() {
        return membersTableName;
    }

    public String getAdminsTableName() {
        return adminsTableName;
    }

    public String getMessagesTableName() {
        return messagesTableName;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@").append(id).append("\n");
        stringBuilder.append(groupName).append("\n");
        stringBuilder.append(biography).append("\n");
        stringBuilder.append("owner: @").append(ownerID);
        return stringBuilder.toString();
    }
}
