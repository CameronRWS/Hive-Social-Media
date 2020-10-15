package hive.app.interest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
	
    @Autowired
    InterestRepository interestRepository;
    
    public List<Interest> findAll(){
        return interestRepository.findAll();
    }

    public Interest findByInterestId(String id){
        int interestId = Integer.parseInt(id);
        return interestRepository.findOne(interestId);
    }
    
    public Interest create(Map<String, String> body){
        String interestText = body.get("interestText");
        return interestRepository.save(new Interest(interestText));
    }

    public Interest update(Map<String, String> body){
        int interestId = Integer.parseInt(body.get("interestId"));
        Interest interest = interestRepository.findOne(interestId);
        String interestText = body.get("interestText");
        interest.setInterestText(interestText);
        return interestRepository.save(interest);
    }

    public boolean delete(Map<String, String> body){
        int interestId = Integer.parseInt(body.get("interestId"));
        interestRepository.delete(interestId);
        return true;
    }
}

