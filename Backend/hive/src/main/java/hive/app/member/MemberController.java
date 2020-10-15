
package hive.app.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/members")
    public List<Member> findAll(){
        return memberService.findAll();
    }
    
    @GetMapping("/members/byHiveId/{hiveId}")
    public List<Member> findByHiveId(@PathVariable String hiveId){
        return memberService.findByHiveId(hiveId); 
    }
    
    @GetMapping("/members/byUserId/{userId}")
    public List<Member> findByUserId(@PathVariable String userId){
        return memberService.findByUserId(userId); 
    }
    
    @GetMapping("/memberCount/byHiveId/{hiveId}")
    public Map<String, Object> getHiveMemberCount(@PathVariable String hiveId){
    	return memberService.getHiveMemberCount(hiveId);
    }
    
    @GetMapping("/memberCount/byUserId/{userId}")
    public Map<String, Object> getHiveCount(@PathVariable String userId){
    	return memberService.getUserMemberCount(userId);
    }

    @PostMapping("/members")
    public Member create(@RequestBody Map<String, String> body){
    	return memberService.create(body);
    }
    
    @PutMapping("/members")
    public Member update(@RequestBody Map<String, String> body){
    	return memberService.update(body);
    }


    @DeleteMapping("/members")
    public Boolean delete(@RequestBody Map<String, String> body){
    	return memberService.delete(body);
    }
}