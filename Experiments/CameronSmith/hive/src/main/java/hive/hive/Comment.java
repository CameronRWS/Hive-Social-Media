package hive.hive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private int commentId;
    @Column(name = "post_id")
    private int postId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "text_content")
    private String textContent;
    

    public Comment() {  }
    
    public Comment(int postId, int userId, String textContent) {
        this.setPostId(postId);
        this.setUserId(userId);
        this.setDateCreated(DateTime.GetCurrentDateTime());
        this.setTextContent(textContent);
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int id) {
        this.commentId = id;
    }
    
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", textContent='" + textContent + '\'' +
                '}';
    }
}