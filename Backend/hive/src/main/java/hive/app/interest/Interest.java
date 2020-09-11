package hive.app.interest;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import hive.app.hive.Hive;
import hive.app.utils.DateTime;

@Entity
@Table(name = "interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "interest_id")
	private int interestId;
	@Column(name = "interest_text")
	private String interestText;
    @Column(name = "date_created")
    private String dateCreated;
    
    public Interest() {};
	
	public Interest(String interestText) {
		this.setInterestText(interestText);
        this.setDateCreated(DateTime.GetCurrentDateTime());
	}
	
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
	
    public void setInterestId(int id) {
        this.interestId = id;
    }
    
    public int getInterestId() {
        return this.interestId;
    }
    
    public void setInterestText(String interestText) {
        this.interestText = interestText;
    }
    
    public String getInterestText() {
        return this.interestText;
    }
	
}
