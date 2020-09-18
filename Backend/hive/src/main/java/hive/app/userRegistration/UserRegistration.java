package hive.app.userRegistration;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_registrations")
public class UserRegistration {
	@EmbeddedId 
    private UserRegistrationIdentity userRegistrationIdentity;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    
    public UserRegistration() {  }
    
    public UserRegistration(String email, String password, UserRegistrationIdentity userRegistrationIdentity) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserRegistrationIdentity(userRegistrationIdentity);
    }

    public UserRegistrationIdentity getUserRegistrationIdentity() {
    	return userRegistrationIdentity;
    }
    
    public void setUserRegistrationIdentity(UserRegistrationIdentity userRegistrationIdentity) {
    	this.userRegistrationIdentity = userRegistrationIdentity;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}