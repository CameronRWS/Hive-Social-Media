package hive.hive;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hive")
public class Hive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hive_id")
    private int hiveId;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "hive_name")
    private String hiveName;
    

    public Hive() {  }

    public Hive(int hiveId, String dateCreated, String hiveName) {
        this.setHiveId(hiveId);
        this.setDateCreated(dateCreated);
        this.setHiveName(hiveName);
    }
    
    public Hive(String dateCreated, String hiveName) {
        this.setDateCreated(dateCreated);
        this.setHiveName(hiveName);
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
    

    public String getHiveName() {
        return hiveName;
    }

    public void setHiveName(String hiveName) {
        this.hiveName = hiveName;
    }

    @Override
    public String toString() {
        return "Hive{" +
                "hiveId=" + hiveId +
                ", dateCreated='" + dateCreated + '\'' +
                ", hiveName='" + hiveName + '\'' +
                '}';
    }
}