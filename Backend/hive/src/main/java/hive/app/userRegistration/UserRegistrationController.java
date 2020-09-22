
package hive.app.userRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.user.User;
import hive.app.user.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class UserRegistrationController {

    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/userRegistrations")
    public List<UserRegistration> index(){
        return userRegistrationRepository.findAll();
    }

    @GetMapping("/userRegistrations/byUserId/{id}")
    public UserRegistration findByUserId(@PathVariable String id){
        int userId = Integer.parseInt(id);
        return userRegistrationRepository.findByUserId(userId);
    }
    
    @GetMapping("/userRegistrations/byUserName/{userName}")
    public UserRegistration findByUserName(@PathVariable String userName){
        return userRegistrationRepository.findByUserName(userName);
    }
    
    @GetMapping("/userRegistrations/byEmail/{email:.+}")
    public UserRegistration findByEmail(@PathVariable String email){
        return userRegistrationRepository.findByEmail(email);
    }
    
    @PostMapping("/userRegistrations")
    public UserRegistration create(@RequestBody Map<String, String> body){
        String email = body.get("email");
        String password = body.get("password");
        User user;
        if(body.get("userId") == null) {
        	Random rand = new Random();
        	int number = rand.nextInt(1000000);
        	user = new User("newUser" + number, "New User " + number, "", "", "");
        } else {
            int userId = Integer.parseInt(body.get("userId"));
        	user = userRepository.findOne(userId);
        }
        userRepository.save(user);
        return userRegistrationRepository.save(new UserRegistration(email, password, new UserRegistrationIdentity(user)));
    }
    
    @PutMapping("/userRegistrations")
    public UserRegistration update(@RequestBody Map<String, String> body){
        int userId = Integer.parseInt(body.get("userId"));
        UserRegistration userRegistration = userRegistrationRepository.findByUserId(userId);
        String email = body.get("email");
        String password = body.get("password");
        userRegistration.setEmail(email);
        userRegistration.setPassword(password);
        return userRegistrationRepository.save(userRegistration);
    }

    @DeleteMapping("/userRegistrations")
    public boolean delete(@RequestBody Map<String, String> body){
        int userId = Integer.parseInt(body.get("userId"));
        UserRegistration userRegistration = userRegistrationRepository.findByUserId(userId);
        userRegistrationRepository.delete(userRegistration);
        return true;
    }
}