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

@RestController
public class InterestController {
	
    @Autowired
    InterestService interestService;
    
    @GetMapping("/interests")
    public List<Interest> findAll(){
        return interestService.findAll();
    }

    @GetMapping("/interests/byInterestId/{id}")
    public Interest show(@PathVariable String id){
        return interestService.findByInterestId(id);
    }
    
    @PostMapping("/interests")
    public Interest create(@RequestBody Map<String, String> body){
    	return interestService.create(body);
    }

    @PutMapping("/interests")
    public Interest update(@RequestBody Map<String, String> body){
    	return interestService.update(body);
    }

    @DeleteMapping("/interests")
    public boolean delete(@RequestBody Map<String, String> body){
    	return interestService.delete(body);
    }
}

