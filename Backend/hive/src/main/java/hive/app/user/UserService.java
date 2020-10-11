package hive.app.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
   
    public List<User> findAll(){
        return userRepository.findAll();
    }
    
    public User findByUserId(String id){
        int userId = Integer.parseInt(id);
        return userRepository.findOne(userId);
    }
    
    public User create(Map<String, String> body){
        String userName = body.get("userName");
        String displayName = body.get("displayName");
        String birthday = body.get("birthday");
        String biography = body.get("biography");
        String location = body.get("location");
        return userRepository.save(new User(userName, displayName, birthday, biography, location));
    }
   
    public User update(Map<String, String> body){
    	int userId = Integer.parseInt(body.get("userId"));
        String userName = body.get("userName");
        String displayName = body.get("displayName");
        String birthday = body.get("birthday");
        String biography = body.get("biography");
        String location = body.get("location");
        User user = userRepository.findOne(userId);
        user.setUserName(userName);
        user.setDisplayName(displayName);
        user.setBirthday(birthday);
        user.setBiography(biography);
        user.setLocation(location);
        return userRepository.save(user);
    }

    public boolean delete(Map<String, String> body){
        int userId = Integer.parseInt(body.get("userId"));
        userRepository.delete(userId);
        return true;
    }
}
