package hive.hive;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;




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
    
    public int getHiveId() {
        return memberIdentity.getHiveId();
    }

    public void setHiveId(int HiveId) {
        this.memberIdentity.setHiveId(HiveId);
    }
    
    public int getUserId() {
    	return memberIdentity.getUserId();
    }

    public void setUserId(int userId) {
    	this.memberIdentity.setUserId(userId);
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
    
    @Override
    public String toString() {
        return "Member{" +
                ", hiveId='" + this.memberIdentity.getHiveId() + '\'' +
                ", userId='" + this.memberIdentity.getUserId() + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", isModerator='" + isModerator + '\'' +
                '}';
    }
}