
package hive.app.hiveInterest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the HiveInterest entity")
@RestController
public class HiveInterestController {

    @Autowired
    HiveInterestService hiveInterestService;
    
    @ApiOperation(value="Get all hiveInterests")
    @GetMapping("/hiveInterests")
    public List<HiveInterest> index(){
        return hiveInterestService.findAll();
    }
    
    @ApiOperation(value="Get all hiveInterests by it's hiveId")
    @GetMapping("/hiveInterests/byHiveId/{hiveId}")
    public List<HiveInterest> getHiveInterestsByHiveId(@PathVariable String hiveId){
        return hiveInterestService.findByHiveId(hiveId); 
    }
    
    @ApiOperation(value="Get all hiveInterests by it's interestId")
    @GetMapping("/hiveInterests/byInterestId/{interestId}")
    public List<HiveInterest> getHiveInterestsByInterestId(@PathVariable String interestId){
        return hiveInterestService.findByInterestId(interestId); 
    }

    @ApiOperation(value="Create a hiveInterest")
    @PostMapping("/hiveInterests")
    public HiveInterest create(@RequestBody Map<String, String> body){
        return hiveInterestService.create(body);
    }

    @ApiOperation(value="Delete a hiveInterest")
    @DeleteMapping("/hiveInterests")
    public boolean delete(@RequestBody Map<String, String> body){
        return hiveInterestService.delete(body);
    }
}