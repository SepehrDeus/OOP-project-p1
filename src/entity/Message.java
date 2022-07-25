package entity;

public class Message {
    private String text;
    private String time;
    private String senderID;
    private String receiverID;

    public Message(String text, String senderID, String receiverID) {
        this.text = text;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.time = new Time().toString();
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }
}
