
package hive.hive;

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

    @GetMapping("/users/{id}")
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
        return userRepository.save(new User(userName, displayName, birthday, biography));
    }

    @DeleteMapping("users/{id}")
    public boolean delete(@PathVariable String id){
        int userId = Integer.parseInt(id);
        userRepository.delete(userId);
        return true;
    }
}