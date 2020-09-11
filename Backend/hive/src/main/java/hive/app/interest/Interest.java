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

@Entity
@Table(name = "interests")
public class Interest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "interest_id")
	private int interestId;
	@Column(name = "interest_text")
	private String interestText;
	
//    @ManyToMany(mappedBy = "interests")
//    private List<Hive> hives;
    
    public Interest() {};
	
	public Interest(String interestText) {
		this.setInterestText(interestText);
	}
	
//    public void setHives(List<Hive> hives) {
//        this.hives = hives;
//    }
//    
//    public List<Hive> getHives() {
//        return this.hives;
//    }
	
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
