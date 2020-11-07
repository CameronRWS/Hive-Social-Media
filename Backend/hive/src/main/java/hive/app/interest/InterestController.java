package hive.app.interest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="REST APIs related to the Interest entity")
@RestController
public class InterestController {
	
    @Autowired
    InterestService interestService;
    
    @ApiOperation(value="Get all interests")
    @GetMapping("/interests")
    public List<Interest> findAll(){
        return interestService.findAll();
    }

    @ApiOperation(value="Get an interest by it's interestId")
    @GetMapping("/interests/byInterestId/{id}")
    public Interest show(@PathVariable String id){
        return interestService.findByInterestId(id);
    }
    
    @ApiOperation(value="Create an interest")
    @PostMapping("/interests")
    public Interest create(@RequestBody Map<String, String> body){
    	return interestService.create(body);
    }

    @ApiOperation(value="Update an interest")
    @PutMapping("/interests")
    public Interest update(@RequestBody Map<String, String> body){
    	return interestService.update(body);
    }

    @ApiOperation(value="Delete an interest")
    @DeleteMapping("/interests")
    public boolean delete(@RequestBody Map<String, String> body){
    	return interestService.delete(body);
    }
}

