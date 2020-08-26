package hive.hive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int postId;
    @Column(name = "hive_id")
    private int hiveId;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "text_content")
    private String textContent;
    

    public Post() {  }

    public Post(int postId, int hiveId, int userId, String dateCreated, String textContent) {
        this.setpostId(postId);
        this.setHiveId(hiveId);
        this.setUserId(userId);
        this.setDateCreated(dateCreated);
        this.setContent(textContent);
    }
    
    public Post(int hiveId, int userId, String dateCreated, String textContent) {
        this.setHiveId(hiveId);
        this.setUserId(userId);
        this.setDateCreated(dateCreated);
        this.setContent(textContent);
    }

    public int getpostId() {
        return postId;
    }

    public void setpostId(int id) {
        this.postId = id;
    }
    
    public int getHiveId() {
        return hiveId;
    }

    public void setHiveId(int hiveId) {
        this.hiveId = hiveId;
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
    

    public String getContent() {
        return textContent;
    }

    public void setContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId='" + userId + '\'' +
                ", textContent='" + textContent + '\'' +
                '}';
    }
}