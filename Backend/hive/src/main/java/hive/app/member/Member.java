package hive.app.member;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import hive.app.hive.Hive;
import hive.app.user.User;
import hive.app.utils.DateTime;




@Entity
@Table(name = "members")
public class Member {

	
	@EmbeddedId 
	private MemberIdentity memberIdentity;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "is_moderator")
    private Boolean isModerator;
    
    public Member() {  }
    
    public Member(MemberIdentity memberIdentity, Boolean isModerator) {
        this.memberIdentity = memberIdentity;
        this.setIsModerator(isModerator);
        this.setDateCreated(DateTime.GetCurrentDateTime());
    }
    
    public Hive getHive() {
        return memberIdentity.getHive();
    }

    public void setHive(Hive hive) {
        this.memberIdentity.setHive(hive);
    }
    
    public User getUser() {
    	return memberIdentity.getUser();
    }

    public void setUser(User user) {
    	this.memberIdentity.setUser(user);
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    } 

    public Boolean getIsModerator() {
        return isModerator;
    }

    public void setIsModerator(Boolean isModerator) {
        this.isModerator = isModerator;
    } 
}