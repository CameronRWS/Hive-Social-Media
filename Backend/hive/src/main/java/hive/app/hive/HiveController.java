
package hive.app.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Hive entity")
@RestController
public class HiveController {

    @Autowired
    HiveService hiveService;
    
    @ApiOperation(value="Get all hives")
    @GetMapping("/hives")
    public List<Hive> findAll(){
        return hiveService.findAll();
    }

    @ApiOperation(value="Get a hive by it's hiveId")
    @GetMapping("/hives/byHiveId/{id}")
    public Hive findByHiveId(@PathVariable String id){
        return hiveService.findByHiveId(id);
    }
    
    @ApiOperation(value="Create a hive")
    @PostMapping("/hives")
    public Hive create(@RequestBody Map<String, String> body){
    	return hiveService.create(body);
    }
    
    @ApiOperation(value="Update a hive")
    @PutMapping("/hives")
    public Hive update(@RequestBody Map<String, String> body){
    	return hiveService.update(body);
    }

    @ApiOperation(value="Delete a hive")
    @DeleteMapping("hives")
    public boolean delete(@RequestBody Map<String, String> body) {
    	return hiveService.delete(body);
    }
}