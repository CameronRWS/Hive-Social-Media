package hive.app.interest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "interests")
public class Interest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "interest_id")
	private int interestId;
	@Column(name = "interest_text")
	private String interestText;
    
    public Interest() {};
	
	public Interest(String interestText) {
		this.setInterestText(interestText);
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
