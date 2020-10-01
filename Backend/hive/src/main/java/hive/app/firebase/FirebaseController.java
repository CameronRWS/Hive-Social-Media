package hive.app.firebase;
import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.hive.Hive;

@RestController
public class FirebaseController {

	@Autowired
	FirebaseService fireBaseService;
	
	@PostMapping("/images")
    public boolean create(@RequestBody Map<String, String> body){
        String filename = body.get("file");
        File file = new File(filename);
        return true;
    }
}
