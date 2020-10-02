
package hive.app.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.comment.Comment;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.post.Post;
import hive.app.post.PostRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LikeController {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    
    @GetMapping("/likeCount/byUserId/{userId}")
    public Map<String, Object> getLikeCountByUserId(@PathVariable String userId) {
    	Integer theUserId = Integer.parseInt(userId);
    	Map<String, Object> map = new HashMap<>();
    	map.put("likeCount", likeRepository.getLikeCountByUserId(theUserId));
        return map;
    }
    
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
        Post post = postRepository.findOne(postId);
        User user = userRepository.findOne(userId);
        LikeIdentity likeIdentity = new LikeIdentity(postId, user);
        System.out.println(likeRepository.findOne(likeIdentity) == null);
        if(likeRepository.findOne(likeIdentity) == null && post.getUser().getUserId() != userId) {
            notificationRepository.save(new Notification(post.getUser().getUserId(), userId, postId, "post-likeReceived"));
        }
        return likeRepository.save(new Like(likeIdentity));
    }

    @DeleteMapping("/likes")
    public boolean delete(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        LikeIdentity likeIdentity = new LikeIdentity(postId, user);
        likeRepository.delete(likeIdentity);
        return true;
    }


}