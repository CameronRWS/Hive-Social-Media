package hive.app.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hive.app.hive.Hive;
import hive.app.user.User;

@Embeddable
public class MemberIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name="hive_id", nullable=false)
    private Hive hive;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    public MemberIdentity() { };
    
    public MemberIdentity(Hive hive, User user) {
    	this.hive = hive;
    	this.user = user;
    }
    
    public Hive getHive() {
    	return this.hive;
    }
    
    public void setHive(Hive hive) {
    	this.hive = hive;
    }
    
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
}