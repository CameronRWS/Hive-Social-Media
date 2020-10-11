
package hive.app.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HiveService {

    @Autowired
    HiveRepository hiveRepository;
    
    public List<Hive> findAll(){
        return hiveRepository.findAll();
    }

    public Hive findByHiveId(String id){
        int hiveId = Integer.parseInt(id);
        return hiveRepository.findOne(hiveId);
    }
    
    public Hive create(Map<String, String> body){
        String name = body.get("name");
        String description = body.get("description");
        String type = body.get("type");
        Double latitude = Double.parseDouble(body.get("latitude"));
        Double longitude = Double.parseDouble(body.get("longitude"));
        return hiveRepository.save(new Hive(name, description, type, latitude, longitude));
    }
    
    public Hive update(Map<String, String> body){
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

    public boolean delete(Map<String, String> body) {
        int hiveId = Integer.parseInt(body.get("hiveId"));
        hiveRepository.delete(hiveId);
        return true;
    }
}