
package hive.app.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    LikeService likeService;
    
    @GetMapping("/likeCount/byUserId/{userId}")
    public Map<String, Object> getLikeCountByUserId(@PathVariable String userId) {
    	return likeService.getLikeCountByUserId(userId);
    }
    
    @GetMapping("/likes")
    public List<Like> findAll(){
        return likeService.findAll();
    }
    
    @GetMapping("/likes/byPostId/{postId}")
    public List<Like> getLikesByPostId(@PathVariable String postId){
        return likeService.findByPostId(postId); 
    }
    
    @GetMapping("/likes/byUserId/{userId}")
    public List<Like> getLikesByUserId(@PathVariable String userId){
        return likeService.findByUserId(userId); 
    }

    @PostMapping("/likes")
    public Like create(@RequestBody Map<String, String> body){
    	return likeService.create(body);
    }

    @DeleteMapping("/likes")
    public boolean delete(@RequestBody Map<String, String> body){
    	return likeService.delete(body);
    }
}