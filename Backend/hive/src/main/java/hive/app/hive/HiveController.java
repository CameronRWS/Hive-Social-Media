
package hive.app.hive;

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

    @GetMapping("/hives/byHiveId/{id}")
    public Hive show(@PathVariable String id){
        int hiveId = Integer.parseInt(id);
        return hiveRepository.findOne(hiveId);
    }
    
    @PostMapping("/hives")
    public Hive create(@RequestBody Map<String, String> body){
        String name = body.get("name");
        String description = body.get("description");
        String type = body.get("type");
        Double latitude = Double.parseDouble(body.get("latitude"));
        Double longitude = Double.parseDouble(body.get("longitude"));
        return hiveRepository.save(new Hive(name, description, type, latitude, longitude));
    }
    
    @PutMapping("/hives")
    public Hive update(@RequestBody Map<String, String> body){
    	int hiveId = Integer.parseInt(body.get("hiveId"));
        String name = body.get("name");
        String description = body.get("description");
        String type = body.get("type");
        Double latitude = Double.parseDouble(body.get("latitude"));
        Double longitude = Double.parseDouble(body.get("longitude"));
        Hive hive = hiveRepository.findOne(hiveId);
        hive.setName(name);
        hive.setDescription(description);
        hive.setType(type);
        hive.setLatitude(latitude);
        hive.setLongitude(longitude);
        return hiveRepository.save(hive);
    }

    @DeleteMapping("hives")
    public boolean delete(@RequestBody Map<String, String> body) {
        int hiveId = Integer.parseInt(body.get("hiveId"));
        hiveRepository.delete(hiveId);
        return true;
    }
}