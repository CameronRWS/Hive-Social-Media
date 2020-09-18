package hive.app.userRegistration;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import hive.app.user.User;

@Embeddable
public class UserRegistrationIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    
    public UserRegistrationIdentity() { };
    
    public UserRegistrationIdentity(User user) {
    	this.user = user;
    }
    
    public User getUser() {
    	return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
}