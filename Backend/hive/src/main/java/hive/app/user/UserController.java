
package hive.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the User entity")
@RestController
public class UserController {

    @Autowired
    UserService userService;
    
    @ApiOperation(value="Get all users")
    @GetMapping("/users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @ApiOperation(value="Get a user by it's userId")
    @GetMapping("/users/byUserId/{id}")
    public User findByUserId(@PathVariable String id){
        return userService.findByUserId(id);
    }
    
    @ApiOperation(value="Create a user")
    @PostMapping("/users")
    public User create(@RequestBody Map<String, String> body){
    	return userService.create(body);
    }
    
    @ApiOperation(value="Update a user")
    @PutMapping("/users")
    public User update(@RequestBody Map<String, String> body){
    	return userService.update(body);
    }

    @ApiOperation(value="Delete a user")
    @DeleteMapping("/users")
    public boolean delete(@RequestBody Map<String, String> body){
    	return userService.delete(body);
    }
}