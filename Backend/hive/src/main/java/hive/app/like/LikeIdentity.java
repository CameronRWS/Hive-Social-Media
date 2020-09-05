package hive.app.like;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hive.app.user.User;

@Embeddable
public class LikeIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    @Column(name = "post_id")
    private int postId;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    public LikeIdentity() { };
    
    public LikeIdentity(int postId, User user) {
    	this.postId = postId;
    	this.user = user;
    }
    
    public int getPostId() {
    	return postId;
    }
    
    public void setPostId(int postId) {
    	this.postId = postId;
    }
    
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
}