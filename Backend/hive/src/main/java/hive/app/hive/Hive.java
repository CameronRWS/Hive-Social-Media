package hive.app.hive;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import hive.app.interest.Interest;
import hive.app.utils.DateTime;

@Entity
@Table(name = "hives")
public class Hive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hive_id")
    private int hiveId;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
    	name = "hive_interests", 
    	joinColumns = { @JoinColumn(name = "hive_id") }, 
    	inverseJoinColumns = { @JoinColumn(name = "interest_id") } 
    )
    List<Interest> interests;


    public Hive() {  }
    
    public Hive(String name, String description, String type, Double latitude, Double longitude) {
        this.setDateCreated(DateTime.GetCurrentDateTime());
        this.setName(name);
        this.setDescription(description);
        this.setType(type);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    
    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }
    
    public int getHiveId() {
        return hiveId;
    }

    public void setHiveId(int hiveId) {
        this.hiveId = hiveId;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}