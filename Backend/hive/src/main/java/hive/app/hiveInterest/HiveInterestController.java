
package hive.app.hiveInterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HiveInterestController {

    @Autowired
    HiveInterestService hiveInterestService;
    
    @GetMapping("/hiveInterests")
    public List<HiveInterest> index(){
        return hiveInterestService.findAll();
    }
    
    @GetMapping("/hiveInterests/byHiveId/{hiveId}")
    public List<HiveInterest> getHiveInterestsByHiveId(@PathVariable String hiveId){
        return hiveInterestService.findByHiveId(hiveId); 
    }
    
    @GetMapping("/hiveInterests/byInterestId/{interestId}")
    public List<HiveInterest> getHiveInterestsByInterestId(@PathVariable String interestId){
        return hiveInterestService.findByInterestId(interestId); 
    }

    @PostMapping("/hiveInterests")
    public HiveInterest create(@RequestBody Map<String, String> body){
        return hiveInterestService.create(body);
    }

    @DeleteMapping("/hiveInterests")
    public boolean delete(@RequestBody Map<String, String> body){
        return hiveInterestService.delete(body);
    }
}