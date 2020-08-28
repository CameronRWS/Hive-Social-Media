
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

    @GetMapping("/hives/{id}")
    public Hive show(@PathVariable String id){
        int hiveId = Integer.parseInt(id);
        return hiveRepository.findOne(hiveId);
    }
    
    @PostMapping("/hives")
    public Hive create(@RequestBody Map<String, String> body){
        String dateCreated = body.get("dateCreated");
        String hiveName = body.get("hiveName");
        return hiveRepository.save(new Hive(dateCreated, hiveName));
    }

    @DeleteMapping("hives/{id}")
    public boolean delete(@PathVariable String id){
        int hiveId = Integer.parseInt(id);
        hiveRepository.delete(hiveId);
        return true;
    }


}