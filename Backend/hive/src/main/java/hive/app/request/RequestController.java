package hive.app.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
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
	@Autowired
	HiveRepository hiveRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	MemberRepository memberRepository;
	
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
		String requestMessage = body.get("requestMessage");
		User user = userRepository.findOne(userId);
		RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
		return requestRepository.save(new Request(requestIdentity, requestMessage));
	}
	
	@PutMapping("/requests")
    public Request update(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
        Request request = requestRepository.findOne(requestIdentity);
        request.setRequestMessage(body.get("requestMessage"));
        return requestRepository.save(request);
    }
	
	@DeleteMapping("/requests")
	public boolean delete(@RequestBody Map<String, String> body) {
		int hiveId = Integer.parseInt(body.get("hiveId"));
		int userId = Integer.parseInt(body.get("userId"));
		String status = body.get("status");
		Hive hive = hiveRepository.findOne(hiveId);
		User user = userRepository.findOne(userId);
		RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
		if(status.equals("accepted")) {
			MemberIdentity memberIdentity = new MemberIdentity(hive, user);
			Member member = new Member(memberIdentity, false);
			memberRepository.save(member);
	        notificationRepository.save(new Notification(userId, hiveId, "request-accepted", "Your request to join the hive " + hive.getName() + " was accepted.", true));
		} else {
	        notificationRepository.save(new Notification(userId, hiveId, "request-declined", "Your request to join the hive " + hive.getName() + " was declined.", true));
		}
		requestRepository.delete(requestIdentity);
		return true;
	}
}
