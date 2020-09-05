package hive.app.post;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import hive.app.comment.Comment;
import hive.app.like.Like;
import hive.app.user.User;
import hive.app.utils.DateTime;


@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private int postId;
    @Column(name = "hive_id")
    private int hiveId;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "title")
    private String title;
    @Column(name = "text_content")
    private String textContent;
    
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
   
    @OneToMany
    @JoinColumn(name="post_id", referencedColumnName = "post_id")
    private List<Comment> comments;
    
    @OneToMany
    @JoinColumn(name="post_id", referencedColumnName = "post_id")
    private List<Like> likes;
    
    public Post() {  }
    
    public Post(int hiveId, User user, String title, String textContent) {
        this.setHiveId(hiveId);
        this.setUser(user);
        this.setDateCreated(DateTime.GetCurrentDateTime());
        this.setTitle(title);
        this.setTextContent(textContent);
    }
    
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    
    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int id) {
        this.postId = id;
    }
    
    public int getHiveId() {
        return hiveId;
    }

    public void setHiveId(int hiveId) {
        this.hiveId = hiveId;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", hiveId='" + hiveId + '\'' +
                ", title='" + title + '\'' +
                ", textContent='" + textContent + '\'' +
                '}';
    }
}