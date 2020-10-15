
package hive.app.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HiveController {

    @Autowired
    HiveService hiveService;
    
    @GetMapping("/hives")
    public List<Hive> findAll(){
        return hiveService.findAll();
    }

    @GetMapping("/hives/byHiveId/{id}")
    public Hive findByHiveId(@PathVariable String id){
        return hiveService.findByHiveId(id);
    }
    
    @PostMapping("/hives")
    public Hive create(@RequestBody Map<String, String> body){
    	return hiveService.create(body);
    }
    
    @PutMapping("/hives")
    public Hive update(@RequestBody Map<String, String> body){
    	return hiveService.update(body);
    }

    @DeleteMapping("hives")
    public boolean delete(@RequestBody Map<String, String> body) {
    	return hiveService.delete(body);
    }
}