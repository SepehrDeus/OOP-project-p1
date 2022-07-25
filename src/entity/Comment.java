package entity;

public class Comment {
    private String post_OR_commentID;//the one we are leaving comment for
    private String CommentID;
    private String CommenterID;// userID
    private String comment_caption;
    private int likesNum;
    private  int commentsNum;
    private final String time ;

    public Comment(String postOR_commentID, String CommentID, String CommenterID, String comment_caption, String time){
        this.post_OR_commentID=postOR_commentID;
        this.CommenterID= CommenterID;
        this.comment_caption=comment_caption;
        this.commentsNum=this.likesNum=0;
        this.CommentID=CommentID;
        this.time = time;
    }

    public String getCommentID() {
        return CommentID;
    }

    public void setCommentID(String commentID) {
        CommentID = commentID;
    }

    public String getCommenterID() {
        return CommenterID;
    }

    public void setCommenterID(String commenterID) {
        CommenterID = commenterID;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public String getTime() {
        return time;
    }

    public String getPost_OR_commentID() {
        return post_OR_commentID;
    }

    public void setPost_OR_commentID(String post_OR_commentID) {
        this.post_OR_commentID = post_OR_commentID;
    }

    public String getComment_caption() {
        return comment_caption;
    }

    public void setComment_caption(String comment_caption) {
        this.comment_caption = comment_caption;
    }
}
