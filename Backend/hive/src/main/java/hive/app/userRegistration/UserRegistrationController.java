
package hive.app.userRegistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the UserRegistration entity")
@RestController
public class UserRegistrationController {

    @Autowired
    UserRegistrationService userRegService;

    @ApiOperation(value="Get all userRegistrations")
    @GetMapping("/userRegistrations")
    public List<UserRegistration> findAll(){
        return userRegService.findAll();
    }

    @ApiOperation(value="Get a userRegistration by it's userId")
    @GetMapping("/userRegistrations/byUserId/{id}")
    public UserRegistration findByUserId(@PathVariable String id){
        return userRegService.findByUserId(id);
    }
    
    @ApiOperation(value="Get a userRegistration by it's userName")
    @GetMapping("/userRegistrations/byUserName/{userName}")
    public UserRegistration findByUserName(@PathVariable String userName){
        return userRegService.findByUserName(userName);
    }
    
    @ApiOperation(value="Get a userRegistration by it's email")
    @GetMapping("/userRegistrations/byEmail/{email:.+}")
    public UserRegistration findByEmail(@PathVariable String email){
        return userRegService.findByEmail(email);
    }
    
    @ApiOperation(value="Create a userRegistration")
    @PostMapping("/userRegistrations")
    public UserRegistration create(@RequestBody Map<String, String> body){
    	return userRegService.create(body);
    }
    
    @ApiOperation(value="Update a userRegistration")
    @PutMapping("/userRegistrations")
    public UserRegistration update(@RequestBody Map<String, String> body){
        return userRegService.update(body);
    }

    @ApiOperation(value="Delete a userRegistration")
    @DeleteMapping("/userRegistrations")
    public boolean delete(@RequestBody Map<String, String> body){
        return userRegService.delete(body);
    }
}