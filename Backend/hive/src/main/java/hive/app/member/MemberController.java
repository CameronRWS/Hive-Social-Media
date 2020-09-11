
package hive.app.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HiveRepository hiveRepository;
    
    
    @GetMapping("/members")
    public List<Member> index(){
        return memberRepository.findAll();
    }
    
    @GetMapping("/members/byHiveId/{hiveId}")
    public List<Member> getMembersByHiveId(@PathVariable String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        return memberRepository.findByHiveId(theHiveId); 
    }
    
    @GetMapping("/members/byUserId/{userId}")
    public List<Member> getMembersByUserId(@PathVariable String userId){
        int theUserId = Integer.parseInt(userId);
        return memberRepository.findByUserId(theUserId); 
    }

    @PostMapping("/members")
    public Member create(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Hive hive = hiveRepository.findOne(hiveId);
        User user = userRepository.findOne(userId);
        Boolean isModerator = Boolean.parseBoolean(body.get("isModerator"));
        MemberIdentity memberIdentity = new MemberIdentity(hive, user);
        return memberRepository.save(new Member(memberIdentity, isModerator));
    }
    
    @PutMapping("/members")
    public Member update(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Hive hive = hiveRepository.findOne(hiveId);
        User user = userRepository.findOne(userId);
        Boolean isModerator = Boolean.parseBoolean(body.get("isModerator"));
        MemberIdentity memberIdentity = new MemberIdentity(hive, user);
        Member member = memberRepository.findOne(memberIdentity);
        member.setIsModerator(isModerator);
        return memberRepository.save(member);
    }


    @DeleteMapping("/members")
    public Boolean delete(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Hive hive = hiveRepository.findOne(hiveId);
        User user = userRepository.findOne(userId);
        MemberIdentity memberIdentity = new MemberIdentity(hive, user);
        memberRepository.delete(memberIdentity);
        return true;
    }


}