package hive.app.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RequestController {
	
	@Autowired
	RequestService requestService;
	
	@GetMapping("/requests")
	public List<Request> findAll() {
		return requestService.findAll();
	}
	
	@GetMapping("/requests/byHiveId/{hiveId}")
	public List<Request> findByHiveId(@PathVariable String hiveId){
		return requestService.findByHiveId(hiveId);
	}
	
	@GetMapping("/requests/byUserId/{userId}")
	public List<Request> findByUserId(@PathVariable String userId){
		return requestService.findByUserId(userId);
	}
	
	@PostMapping("/requests")
	public Request create(@RequestBody Map<String, String> body) {
		return requestService.create(body);
	}
	
	@PutMapping("/requests")
    public Request update(@RequestBody Map<String, String> body){
        return requestService.update(body);
    }
	
	@DeleteMapping("/requests")
	public boolean delete(@RequestBody Map<String, String> body) {
		return requestService.delete(body);
	}
}
