package hive.app.request;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import hive.app.user.User;
import hive.app.utils.DateTime;

@Entity
@Table(name = "requests")
public class Request {

	@EmbeddedId
	private RequestIdentity requestIdentity;
	
	@Column (name = "date_created")
	private String dateCreated;
	
	public Request() {}
	
	public Request(RequestIdentity requestIdentity){
		this.requestIdentity = requestIdentity;
		this.dateCreated = DateTime.GetCurrentDateTime();
	}
	
	public int getHiveId() {
		return requestIdentity.getHiveId();
	}
	
	public void setHiveId(int hiveId) {
		requestIdentity.setHiveId(hiveId);
	}
	
	public User getUser() {
		return requestIdentity.getUser();
	}
	
	public void setUser(User user) {
		requestIdentity.setUser(user);
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
}