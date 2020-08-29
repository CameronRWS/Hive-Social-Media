
package hive.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HiveController {

    @Autowired
    HiveRepository hiveRepository;
    
    @GetMapping("/hives")
    public List<Hive> index(){
        return hiveRepository.findAll();
    }

    @GetMapping("/hives/hive_id/{id}")
    public Hive show(@PathVariable String id){
        int hiveId = Integer.parseInt(id);
        return hiveRepository.findOne(hiveId);
    }
    
    
    @PostMapping("/hives")
    public Hive create(@RequestBody Map<String, String> body){
        String name = body.get("name");
        String description = body.get("description");
        String type = body.get("type");
        String coordinates = body.get("coordinates");
        return hiveRepository.save(new Hive(name, description, type, coordinates));
    }
    
    @PutMapping("/hives")
    public Hive update(@RequestBody Map<String, String> body){
    	int hiveId = Integer.parseInt(body.get("hiveId"));
        String name = body.get("name");
        String description = body.get("description");
        String type = body.get("type");
        String coordinates = body.get("coordinates");
        Hive hive = hiveRepository.findOne(hiveId);
        hive.setName(name);
        hive.setDescription(description);
        hive.setType(type);
        hive.setCoordinates(coordinates);
        return hiveRepository.save(hive);
    }

    @DeleteMapping("hives")
    public boolean delete(@RequestBody Map<String, String> body) {
        int hiveId = Integer.parseInt(body.get("hiveId"));
        hiveRepository.delete(hiveId);
        return true;
    }
}