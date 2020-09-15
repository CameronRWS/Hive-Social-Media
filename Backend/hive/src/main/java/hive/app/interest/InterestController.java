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
    InterestRepository interestRepository;
    
    @GetMapping("/interests")
    public List<Interest> index(){
        return interestRepository.findAll();
    }

    @GetMapping("/interests/byInterestId/{id}")
    public Interest show(@PathVariable String id){
        int interestId = Integer.parseInt(id);
        return interestRepository.findOne(interestId);
    }
    
    @PostMapping("/interests")
    public Interest create(@RequestBody Map<String, String> body){
        String interestText = body.get("interestText");
        return interestRepository.save(new Interest(interestText));
    }

    @PutMapping("/interests")
    public Interest update(@RequestBody Map<String, String> body){
        int interestId = Integer.parseInt(body.get("interestId"));
        Interest interest = interestRepository.findOne(interestId);
        String interestText = body.get("interestText");
        interest.setInterestText(interestText);
        return interestRepository.save(interest);
    }

    @DeleteMapping("/Interests")
    public boolean delete(@RequestBody Map<String, String> body){
        int interestId = Integer.parseInt(body.get("interestId"));
        interestRepository.delete(interestId);
        return true;
    }
}

