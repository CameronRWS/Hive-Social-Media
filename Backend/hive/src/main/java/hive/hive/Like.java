package hive.hive;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;




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
        this.setDateCreated(DateTime.GetCurrentDateTime());
    }
    
    public int getPostId() {
        return likeIdentity.getPostId();
    }

    public void setPostId(int postId) {
        this.likeIdentity.setPostId(postId);
    }
    
    public int getUserId() {
    	return likeIdentity.getUserId();
    }

    public void setUserId(int userId) {
    	this.likeIdentity.setUserId(userId);
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    } 

    @Override
    public String toString() {
        return "Like{" +
                ", postId='" + this.likeIdentity.getPostId() + '\'' +
                ", userId='" + this.likeIdentity.getUserId() + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}