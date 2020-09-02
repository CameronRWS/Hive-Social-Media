package hive.hive;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LikeIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    private int postId;
    private int userId;
    
    public LikeIdentity() { };
    
    public LikeIdentity(int postId, int userId) {
    	this.postId = postId;
    	this.userId = userId;
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
}