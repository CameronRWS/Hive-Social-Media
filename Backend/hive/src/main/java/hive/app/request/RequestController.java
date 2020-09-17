package hive.app.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import hive.app.user.User;
import hive.app.user.UserRepository;
import java.util.List;
import java.util.Map;

@RestController
public class RequestController {
	
	@Autowired
	RequestRepository requestRepository;
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/requests")
	public List<Request> index() {
		return requestRepository.findAll();
	}
	
	@GetMapping("/requests/byHiveId/{hiveId}")
	public List<Request> getRequestsByHiveId(@PathVariable String hiveId){
		int theHiveId = Integer.parseInt(hiveId);
		return requestRepository.findByHiveId(theHiveId);
	}
	
	@GetMapping("/requests/byUserId/{userId}")
	public List<Request> getRequestsByUserId(@PathVariable String userId){
		int theUserId = Integer.parseInt(userId);
		return requestRepository.findByUserId(theUserId);
	}
	
	@PostMapping("/requests")
	public Request create(@RequestBody Map<String, String> body) {
		int hiveId = Integer.parseInt(body.get("hiveId"));
		int userId = Integer.parseInt(body.get("userId"));
		User user = userRepository.findOne(userId);
		RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
		return requestRepository.save(new Request(requestIdentity));
	}
	
	@DeleteMapping("/requests")
	public boolean delete(@RequestBody Map<String, String> body) {
		int hiveId = Integer.parseInt(body.get("hiveId"));
		int userId = Integer.parseInt(body.get("userId"));
		User user = userRepository.findOne(userId);
		RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
		requestRepository.delete(requestIdentity);
		return true;
	}
}
