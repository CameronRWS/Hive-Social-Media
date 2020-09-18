package hive.app.hiveInterest;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HiveInterestIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    @Column(name = "hive_id")
    private int hiveId;
    @Column(name = "interest_id")
    private int interestId;
    
    public HiveInterestIdentity() { };
    
    public HiveInterestIdentity(int hiveId, int interestId) {
    	this.hiveId = hiveId;
    	this.interestId = interestId;
    }
    
    public int getHiveId() {
    	return hiveId;
    }
    
    public void setHiveId(int HiveId) {
    	this.hiveId = HiveId;
    }
    
    public int getInterestId() {
    	return interestId;
    }
    
    public void setInterestId(int interestId) {
    	this.interestId = interestId;
    }
}