package hive.app.like;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import hive.app.user.User;
import hive.app.utils.DateTime;




@Entity
@Table(name = "likes")
public class Like {

	
	@EmbeddedId 
	private LikeIdentity likeIdentity;
    @Column(name = "date_created")
    private String dateCreated;
    
    public Like() {  }
    
    public Like(LikeIdentity likeIdentity) {
        this.likeIdentity = likeIdentity;
        this.setDateCreated(DateTime.getCurrentDateTime());
    }
    
    public int getPostId() {
        return likeIdentity.getPostId();
    }

    public void setPostId(int postId) {
        this.likeIdentity.setPostId(postId);
    }
    
    public User getUser() {
    	return likeIdentity.getUser();
    }

    public void setUser(User user) {
    	this.likeIdentity.setUser(user);
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    } 
}