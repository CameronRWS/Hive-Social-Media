
package hive.app.hiveInterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HiveInterestController {

    @Autowired
    HiveInterestRepository hiveInterestRepository;
    
    @GetMapping("/hiveInterests")
    public List<HiveInterest> index(){
        return hiveInterestRepository.findAll();
    }
    
    @GetMapping("/hiveInterests/byHiveId/{hiveId}")
    public List<HiveInterest> getHiveInterestsByHiveId(@PathVariable String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        return hiveInterestRepository.findByHiveId(theHiveId); 
    }
    
    @GetMapping("/hiveInterests/byInterestId/{interestId}")
    public List<HiveInterest> getHiveInterestsByInterestId(@PathVariable String interestId){
        int theInterestId = Integer.parseInt(interestId);
        return hiveInterestRepository.findByInterestId(theInterestId); 
    }

    @PostMapping("/hiveInterests")
    public HiveInterest create(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int interestId = Integer.parseInt(body.get("interestId"));
        HiveInterestIdentity hiveInterestIdentity = new HiveInterestIdentity(hiveId, interestId);
        return hiveInterestRepository.save(new HiveInterest(hiveInterestIdentity));
    }

    @DeleteMapping("/hiveInterests")
    public boolean delete(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int interestId = Integer.parseInt(body.get("interestId"));
        HiveInterestIdentity hiveInterestIdentity = new HiveInterestIdentity(hiveId, interestId);
        hiveInterestRepository.delete(hiveInterestIdentity);
        return true;
    }


}