package hive.app.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import hive.app.utils.DateTime;


@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "noti_id")
    private int notiId;
    @Column(name = "owner_user_id")
    private int ownerUserId;
    @Column(name = "creator_user_id")
    private int creatorUserId;
    @Column(name = "entity_id")
    private int entityId;
    @Column(name = "noti_type")
    private String notiType;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "is_new")
    private Boolean isNew;
    
    public Notification() {  }
    
    public Notification(int ownerUserId, int creatorUserId, int entityId, String notiType) {
        this.setOwnerUserId(ownerUserId);
        this.setCreatorUserId(creatorUserId);
        this.setEntityId(entityId);
        this.setNotiType(notiType);
        this.setDateCreated(DateTime.getCurrentDateTime());
        this.setIsNew(true);
    }
    
    public int getNotiId() {
        return notiId;
    }

	public void setNotiId(int notiId) {
		this.notiId = notiId;
	}
    
    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
		this.notiType = notiType;		
	}
    
    public int getEntityId() {
        return entityId;
    }

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}
	
    public int getOwnerUserId() {
        return ownerUserId;
    }

	public void setOwnerUserId(int ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	
    public int getCreatorUserId() {
        return creatorUserId;
    }

	public void setCreatorUserId(int creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }
}