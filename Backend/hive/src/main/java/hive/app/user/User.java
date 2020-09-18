package hive.app.user;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import hive.app.utils.DateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "date_created")
    private String dateCreated;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "biography")
    private String biography;
    @Column(name = "location")
    private String location;
    
    

    public User() {  }
    
    public User(String userName, String displayName, String birthday, String biography) {
        this.setDateCreated(DateTime.GetCurrentDateTime());
        this.setUserName(userName);
        this.setDisplayName(displayName);
        this.setBirthday(birthday);
        this.setBiography(biography);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    
    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}