package hive.hive;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
    @Column(name = "coordinates")
    private String coordinates;
    
    @OneToMany
    @JoinColumn(name="hive_id", referencedColumnName = "hive_id")
    private List<Member> members;
    

    public Hive() {  }
    
    public Hive(String name, String description, String type, String coordinates) {
        this.setDateCreated(DateTime.GetCurrentDateTime());
        this.setName(name);
        this.setDescription(description);
        this.setType(type);
        this.setCoordinates(coordinates);
    }
    
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
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
    
    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    
    @Override
    public String toString() {
        return "Hive{" +
                "hiveId=" + hiveId +
                ", dateCreated='" + dateCreated + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", coordinates='" + coordinates + '\'' +
                '}';
    }
}