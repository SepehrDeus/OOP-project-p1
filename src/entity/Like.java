package entity;

public class Like {
    private String post_OR_comment_ID;
    private String Like_ID;
    private String Liker_id;

    public Like(String post_OR_comment_ID, String Like_ID, String Liker_id){
        this.post_OR_comment_ID=post_OR_comment_ID;
        this.Like_ID = Like_ID;
        this.Liker_id = Liker_id;
    }



    public String getLike_ID() {
        return Like_ID;
    }



    public String getPost_OR_comment_ID() {
        return post_OR_comment_ID;
    }

    public void setPost_OR_comment_ID(String post_OR_comment_ID) {
        this.post_OR_comment_ID = post_OR_comment_ID;
    }

    public String getLiker_id() {
        return Liker_id;
    }

    public void setLiker_id(String liker_id) {
        Liker_id = liker_id;
    }
}
