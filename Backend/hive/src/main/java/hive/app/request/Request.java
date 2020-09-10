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

	// for now, generate key as normal. want embedded class after RequestIdentity is created
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comment_id")
	private int requestIdentity;
	
	@Column (name = "date_created")
	private String dateCreated;
	
	//public Request() {}
	
	public Request(){
		this.dateCreated = DateTime.GetCurrentDateTime();
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
}

