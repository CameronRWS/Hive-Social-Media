package hive.app.request;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import hive.app.user.User;

@Embeddable
public class RequestIdentity {
	
	private static final long serializedVersionUID = 1L;
	
	@Column (name = "hive_id")
	private int hiveId;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	public RequestIdentity() {};
	
	public RequestIdentity(int hiveId, User user) {
		this.hiveId = hiveId;
		this.user = user;
	}
	
	public int getHiveId() {
		return hiveId;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setHiveId(int hiveId) {
		this.hiveId = hiveId;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
