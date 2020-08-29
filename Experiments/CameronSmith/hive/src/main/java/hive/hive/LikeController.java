
package hive.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    LikeRepository likeRepository;
    
    @GetMapping("/likes")
    public List<Like> index(){
        return likeRepository.findAll();
    }
    
    @GetMapping("/likes/byPostId/{postId}")
    public List<Like> getLikesByPostId(@PathVariable String postId){
        int thePostId = Integer.parseInt(postId);
        return likeRepository.findByPostId(thePostId); 
    }
    
    @GetMapping("/likes/byUserId/{userId}")
    public List<Like> getLikesByUserId(@PathVariable String userId){
        int theUserId = Integer.parseInt(userId);
        return likeRepository.findByUserId(theUserId); 
    }

    @PostMapping("/likes")
    public Like create(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        LikeIdentity likeIdentity = new LikeIdentity(postId, userId);
        return likeRepository.save(new Like(likeIdentity));
    }

    @DeleteMapping("/likes")
    public boolean delete(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        LikeIdentity likeIdentity = new LikeIdentity(postId, userId);
        likeRepository.delete(likeIdentity);
        return true;
    }


}