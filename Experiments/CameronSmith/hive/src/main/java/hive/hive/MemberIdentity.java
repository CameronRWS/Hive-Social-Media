package hive.hive;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class MemberIdentity implements Serializable {
	private static final long serialVersionUID = 1L;
    private int hiveId;
    private int userId;
    
    public MemberIdentity() { };
    
    public MemberIdentity(int hiveId, int userId) {
    	this.hiveId = hiveId;
    	this.userId = userId;
    }
    
    public int getHiveId() {
    	return hiveId;
    }
    
    public void setHiveId(int hiveId) {
    	this.hiveId = hiveId;
    }
    
    public int getUserId() {
    	return userId;
    }
    
    public void setUserId(int userId) {
    	this.userId = userId;
    }
}