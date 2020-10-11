
package hive.app.hiveInterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HiveInterestService {

    @Autowired
    HiveInterestRepository hiveInterestRepository;
    
    public List<HiveInterest> findAll(){
        return hiveInterestRepository.findAll();
    }
    
    public List<HiveInterest> findByHiveId(String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        return hiveInterestRepository.findByHiveId(theHiveId); 
    }
    
    public List<HiveInterest> findByInterestId(String interestId){
        int theInterestId = Integer.parseInt(interestId);
        return hiveInterestRepository.findByInterestId(theInterestId); 
    }

    public HiveInterest create(Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int interestId = Integer.parseInt(body.get("interestId"));
        HiveInterestIdentity hiveInterestIdentity = new HiveInterestIdentity(hiveId, interestId);
        return hiveInterestRepository.save(new HiveInterest(hiveInterestIdentity));
    }

    public boolean delete(Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int interestId = Integer.parseInt(body.get("interestId"));
        HiveInterestIdentity hiveInterestIdentity = new HiveInterestIdentity(hiveId, interestId);
        hiveInterestRepository.delete(hiveInterestIdentity);
        return true;
    }
}