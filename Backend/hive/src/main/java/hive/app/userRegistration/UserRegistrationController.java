
package hive.app.userRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserRegistrationController {

    @Autowired
    UserRegistrationService userRegService;

    @GetMapping("/userRegistrations")
    public List<UserRegistration> findAll(){
        return userRegService.findAll();
    }

    @GetMapping("/userRegistrations/byUserId/{id}")
    public UserRegistration findByUserId(@PathVariable String id){
        return userRegService.findByUserId(id);
    }
    
    @GetMapping("/userRegistrations/byUserName/{userName}")
    public UserRegistration findByUserName(@PathVariable String userName){
        return userRegService.findByUserName(userName);
    }
    
    @GetMapping("/userRegistrations/byEmail/{email:.+}")
    public UserRegistration findByEmail(@PathVariable String email){
        return userRegService.findByEmail(email);
    }
    
    @PostMapping("/userRegistrations")
    public UserRegistration create(@RequestBody Map<String, String> body){
    	return userRegService.create(body);
    }
    
    @PutMapping("/userRegistrations")
    public UserRegistration update(@RequestBody Map<String, String> body){
        return userRegService.update(body);
    }

    @DeleteMapping("/userRegistrations")
    public boolean delete(@RequestBody Map<String, String> body){
        return userRegService.delete(body);
    }
}