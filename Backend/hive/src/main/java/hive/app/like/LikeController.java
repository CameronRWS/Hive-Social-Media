
package hive.app.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Like entity")
@RestController
public class LikeController {

    @Autowired
    LikeService likeService;
    
    @ApiOperation(value="Get the count of likes across all posts made by a userId")
    @GetMapping("/likeCount/byUserId/{userId}")
    public Map<String, Object> getLikeCountByUserId(@PathVariable String userId) {
    	return likeService.getLikeCountByUserId(userId);
    }
    
    @ApiOperation(value="Get all likes")
    @GetMapping("/likes")
    public List<Like> findAll(){
        return likeService.findAll();
    }
    
    @ApiOperation(value="Get all likes by it's postId")
    @GetMapping("/likes/byPostId/{postId}")
    public List<Like> getLikesByPostId(@PathVariable String postId){
        return likeService.findByPostId(postId); 
    }
    
    @ApiOperation(value="Get all likes by it's userId")
    @GetMapping("/likes/byUserId/{userId}")
    public List<Like> getLikesByUserId(@PathVariable String userId){
        return likeService.findByUserId(userId); 
    }

    @ApiOperation(value="Create a like")
    @PostMapping("/likes")
    public Like create(@RequestBody Map<String, String> body){
    	return likeService.create(body);
    }

    @ApiOperation(value="Delete a like")
    @DeleteMapping("/likes")
    public boolean delete(@RequestBody Map<String, String> body){
    	return likeService.delete(body);
    }
}