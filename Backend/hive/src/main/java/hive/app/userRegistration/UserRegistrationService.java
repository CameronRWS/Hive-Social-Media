package hive.app.userRegistration;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hive.app.user.User;
import hive.app.user.UserRepository;

@Service
public class UserRegistrationService {

    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    UserRepository userRepository;
    
    public List<UserRegistration> findAll() {
    	return userRegistrationRepository.findAll();
    }
    
    public UserRegistration findByUserId(String id){
        int userId = Integer.parseInt(id);
        return userRegistrationRepository.findByUserId(userId);
    }
    
    public UserRegistration findByUserName(String userName){
        return userRegistrationRepository.findByUserName(userName);
    }
    
    public UserRegistration findByEmail(String email){
        return userRegistrationRepository.findByEmail(email);
    }
    
    public UserRegistration create(Map<String, String> body){
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
    
    public UserRegistration update(Map<String, String> body){
        int userId = Integer.parseInt(body.get("userId"));
        UserRegistration userRegistration = userRegistrationRepository.findByUserId(userId);
        String email = body.get("email");
        String password = body.get("password");
        userRegistration.setEmail(email);
        userRegistration.setPassword(password);
        return userRegistrationRepository.save(userRegistration);
    }
    
    public boolean delete(Map<String, String> body){
        int userId = Integer.parseInt(body.get("userId"));
        UserRegistration userRegistration = userRegistrationRepository.findByUserId(userId);
        userRegistrationRepository.delete(userRegistration);
        return true;
    }
}
