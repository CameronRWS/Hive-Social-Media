package hive.app.hiveInterest;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import hive.app.utils.DateTime;

@Entity
@Table(name = "hive_interests")
public class HiveInterest {

	
	@EmbeddedId 
	private HiveInterestIdentity hiveInterestIdentity;
    @Column(name = "date_created")
    private String dateCreated;
    
    public HiveInterest() {  }
    
    public HiveInterest(HiveInterestIdentity hiveInterestIdentity) {
        this.hiveInterestIdentity = hiveInterestIdentity;
        this.setDateCreated(DateTime.GetCurrentDateTime());
    }
    
    public int getHiveId() {
        return hiveInterestIdentity.getHiveId();
    }

    public void setHiveId(int hiveId) {
        this.hiveInterestIdentity.setHiveId(hiveId);
    }
    
    public int getInterestId() {
    	return hiveInterestIdentity.getInterestId();
    }

    public void setUser(int interestId) {
    	this.hiveInterestIdentity.setInterestId(interestId);
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    } 
}