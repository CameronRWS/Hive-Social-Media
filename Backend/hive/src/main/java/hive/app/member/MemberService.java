
package hive.app.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HiveRepository hiveRepository;
    @Autowired
    NotificationRepository notificationRepository;
    
    public List<Member> findAll(){
        return memberRepository.findAll();
    }
    
    public List<Member> findByHiveId(String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        return memberRepository.findByHiveId(theHiveId); 
    }
    
    public List<Member> findByUserId(String userId){
        int theUserId = Integer.parseInt(userId);
        return memberRepository.findByUserId(theUserId); 
    }
    
    public Map<String, Object> getHiveMemberCount(String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        int memberCount = memberRepository.findByHiveId(theHiveId).size();
        Map<String, Object> map = new HashMap<>();
        map.put("memberCount", memberCount);
        return map;
    }
    
    public Map<String, Object> getUserMemberCount(String userId){
        int theUserId = Integer.parseInt(userId);
        int memberCount = memberRepository.findByUserId(theUserId).size();
        Map<String, Object> map = new HashMap<>();
        map.put("memberCount", memberCount);
        return map;
    }

    public Member create(Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Hive hive = hiveRepository.findOne(hiveId);
        User user = userRepository.findOne(userId);
        MemberIdentity memberIdentity = new MemberIdentity(hive, user);
        return memberRepository.save(new Member(memberIdentity, false));
    }
    
    public Member update(Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Hive hive = hiveRepository.findOne(hiveId);
        User user = userRepository.findOne(userId);
        Boolean isModerator = Boolean.parseBoolean(body.get("isModerator"));
        MemberIdentity memberIdentity = new MemberIdentity(hive, user);
        Member member = memberRepository.findOne(memberIdentity);
        if(member.getIsModerator() == false && isModerator == true) {
			notificationRepository.save(new Notification(userId, -1, hiveId, "hive-rolePromotion"));
        } else if(member.getIsModerator() == true && isModerator == false) {
			notificationRepository.save(new Notification(userId, -1, hiveId, "hive-roleDemotion"));
        }
        member.setIsModerator(isModerator);
        return memberRepository.save(member);
    }

    public Boolean delete(Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Hive hive = hiveRepository.findOne(hiveId);
        User user = userRepository.findOne(userId);
        MemberIdentity memberIdentity = new MemberIdentity(hive, user);
        memberRepository.delete(memberIdentity);
        return true;
    }
}