
package hive.app.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.post.Post;
import hive.app.post.PostRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;
import hive.app.websocket.WebSocketServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikeService {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    
    public Map<String, Object> getLikeCountByUserId(String userId) {
    	Integer theUserId = Integer.parseInt(userId);
    	Map<String, Object> map = new HashMap<>();
    	map.put("likeCount", likeRepository.getLikeCountByUserId(theUserId));
        return map;
    }
    
    public List<Like> findAll(){
        return likeRepository.findAll();
    }
    
    public List<Like> findByPostId(String postId){
        int thePostId = Integer.parseInt(postId);
        return likeRepository.findByPostId(thePostId); 
    }
    
    public List<Like> findByUserId(String userId){
        int theUserId = Integer.parseInt(userId);
        return likeRepository.findByUserId(theUserId); 
    }

    public Like create(Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        Post post = postRepository.findOne(postId);
        User user = userRepository.findOne(userId);
        LikeIdentity likeIdentity = new LikeIdentity(postId, user);
        System.out.println(likeRepository.findOne(likeIdentity) == null);
        if(likeRepository.findOne(likeIdentity) == null && post.getUser().getUserId() != userId) {
            notificationRepository.save(new Notification(post.getUser().getUserId(), userId, postId, "post-likeReceived"));
			try {
				WebSocketServer.sendUpdatedNotiCount(post.getUser().getUserId());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return likeRepository.save(new Like(likeIdentity));
    }

    public boolean delete(Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        LikeIdentity likeIdentity = new LikeIdentity(postId, user);
        likeRepository.delete(likeIdentity);
        return true;
    }
}