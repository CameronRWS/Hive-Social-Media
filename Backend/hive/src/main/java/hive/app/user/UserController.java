
package hive.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    
    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/users/byUserId/{id}")
    public User findByUserId(@PathVariable String id){
        return userService.findByUserId(id);
    }
    
    @PostMapping("/users")
    public User create(@RequestBody Map<String, String> body){
    	return userService.create(body);
    }
    
    @PutMapping("/users")
    public User update(@RequestBody Map<String, String> body){
    	return userService.update(body);
    }

    @DeleteMapping("/users")
    public boolean delete(@RequestBody Map<String, String> body){
    	return userService.delete(body);
    }
}