package hive.hive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class MemberIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    @Column(name = "hive_id")
    private int hiveId;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    public MemberIdentity() { };
    
    public MemberIdentity(int hiveId, User user) {
    	this.hiveId = hiveId;
    	this.user = user;
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
}