
package hive.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    MemberRepository memberRepository;
    
    @GetMapping("/members")
    public List<Member> index(){
        return memberRepository.findAll();
    }
    
    @GetMapping("/members/byHiveId/{hiveId}")
    public List<Member> getMembersByHiveId(@PathVariable String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        return memberRepository.findByHiveId(theHiveId); 
    }

    @PostMapping("/members")
    public Member create(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Boolean isModerator = Boolean.parseBoolean(body.get("isModerator"));
        MemberIdentity memberIdentity = new MemberIdentity(hiveId, userId);
        return memberRepository.save(new Member(memberIdentity, isModerator));
    }
    
    @PutMapping("/members")
    public Member update(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        Boolean isModerator = Boolean.parseBoolean(body.get("isModerator"));
        MemberIdentity memberIdentity = new MemberIdentity(hiveId, userId);
        Member member = memberRepository.findOne(memberIdentity);
        member.setIsModerator(isModerator);
        return memberRepository.save(member);
    }


    @DeleteMapping("/members")
    public Boolean delete(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        MemberIdentity memberIdentity = new MemberIdentity(hiveId, userId);
        memberRepository.delete(memberIdentity);
        return true;
    }


}