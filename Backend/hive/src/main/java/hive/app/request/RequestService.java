package hive.app.request;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;
import hive.app.websocket.WebSocketServer;

@Service
public class RequestService {
	
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
	
	public List<Request> findAll() {
		return requestRepository.findAll();
	}
	
	public List<Request> findByHiveId(String hiveId){
		int theHiveId = Integer.parseInt(hiveId);
		return requestRepository.findByHiveId(theHiveId);
	}
	
	public List<Request> findByUserId(String userId){
		int theUserId = Integer.parseInt(userId);
		return requestRepository.findByUserId(theUserId);
	}
	
	public Request create(Map<String, String> body) {
		int hiveId = Integer.parseInt(body.get("hiveId"));
		int userId = Integer.parseInt(body.get("userId"));
		String requestMessage = body.get("requestMessage");
		Hive hive = hiveRepository.findOne(hiveId);
		User user = userRepository.findOne(userId);
		if(memberRepository.findOne(new MemberIdentity(hive, user)) != null) {
			System.out.println("Cannot create a request if you are already apart of the hive.");
			return null;
		}
		List<Member> modsOfHive = memberRepository.findByHiveIdAndIsMod(hiveId);
		for(Member m : modsOfHive) {
			notificationRepository.save(new Notification(m.getUser().getUserId(), userId, hiveId, "hive-requestReceived"));
			try {
				WebSocketServer.sendUpdatedNotiCount(m.getUser().getUserId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
		return requestRepository.save(new Request(requestIdentity, requestMessage));
	}
	
    public Request update(Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        RequestIdentity requestIdentity = new RequestIdentity(hiveId, user);
        Request request = requestRepository.findOne(requestIdentity);
        request.setRequestMessage(body.get("requestMessage"));
        return requestRepository.save(request);
    }
	
	public boolean delete(Map<String, String> body) {
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
	        notificationRepository.save(new Notification(userId, -1, hiveId, "hive-requestAccepted"));
		} else {
	        notificationRepository.save(new Notification(userId, -1, hiveId, "hive-requestDeclined"));
		}
		try {
			WebSocketServer.sendUpdatedNotiCount(userId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Request r = requestRepository.findOne(requestIdentity);
		r.setIsActive(false);
		requestRepository.save(r);
		return true;
	}
}
