
package hive.app.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Member entity")
@RestController
public class MemberController {

    @Autowired
    MemberService memberService;

    @ApiOperation(value="Get all members")
    @GetMapping("/members")
    public List<Member> findAll(){
        return memberService.findAll();
    }
    
    @ApiOperation(value="Get all members by it's hiveId")
    @GetMapping("/members/byHiveId/{hiveId}")
    public List<Member> findByHiveId(@PathVariable String hiveId){
        return memberService.findByHiveId(hiveId); 
    }
    
    @ApiOperation(value="Get all members by it's userId")
    @GetMapping("/members/byUserId/{userId}")
    public List<Member> findByUserId(@PathVariable String userId){
        return memberService.findByUserId(userId); 
    }
    
    @ApiOperation(value="Get the count of members in a hive")
    @GetMapping("/memberCount/byHiveId/{hiveId}")
    public Map<String, Object> getHiveMemberCount(@PathVariable String hiveId){
    	return memberService.getHiveMemberCount(hiveId);
    }
    
    @ApiOperation(value="Get the count of hives a user is apart of")
    @GetMapping("/memberCount/byUserId/{userId}")
    public Map<String, Object> getHiveCount(@PathVariable String userId){
    	return memberService.getUserMemberCount(userId);
    }

    @ApiOperation(value="Create a member")
    @PostMapping("/members")
    public Member create(@RequestBody Map<String, String> body){
    	return memberService.create(body);
    }
    
    @ApiOperation(value="Update a member")
    @PutMapping("/members")
    public Member update(@RequestBody Map<String, String> body){
    	return memberService.update(body);
    }

    @ApiOperation(value="Delete a member")
    @DeleteMapping("/members")
    public Boolean delete(@RequestBody Map<String, String> body){
    	return memberService.delete(body);
    }
}