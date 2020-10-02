
package hive.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/users")
    public List<User> index(){
        return userRepository.findAll();
    }

    @GetMapping("/users/byUserId/{id}")
    public User show(@PathVariable String id){
        int userId = Integer.parseInt(id);
        return userRepository.findOne(userId);
    }
    
    
    @PostMapping("/users")
    public User create(@RequestBody Map<String, String> body){
        String userName = body.get("userName");
        String displayName = body.get("displayName");
        String birthday = body.get("birthday");
        String biography = body.get("biography");
        String location = body.get("location");
        return userRepository.save(new User(userName, displayName, birthday, biography, location));
    }
    
    @PutMapping("/users")
    public User update(@RequestBody Map<String, String> body){
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

    @DeleteMapping("/users")
    public boolean delete(@RequestBody Map<String, String> body){
        int userId = Integer.parseInt(body.get("userId"));
        userRepository.delete(userId);
        return true;
    }
}