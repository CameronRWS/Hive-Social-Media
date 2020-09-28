
package hive.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;
import hive.app.utils.Regex;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    
    @GetMapping("/comments")
    public List<Comment> index(){
        return commentRepository.findAll();
    }

    @GetMapping("/comments/comment_id/{id}")
    public Comment show(@PathVariable String id){
        int commentId = Integer.parseInt(id);
        return commentRepository.findOne(commentId);
    }
    
    @GetMapping("/comments/byPostId/{postId}")
    public List<Comment> getCommentsByUserId(@PathVariable String postId){
        int thePostId = Integer.parseInt(postId);
        return commentRepository.findByPostId(thePostId); 
    }

    @PostMapping("/comments")
    public Comment create(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        String textContent = body.get("textContent");
        notificationRepository.save(new Notification(userId, postId, "comment", "@" + user.getUserName() + " commented on your buzz!", true));
        Comment comment = commentRepository.save(new Comment(postId, user, textContent));
        //create notification for all usernames found in comment
    	List<String> list = Regex.getUserNamesMentionedInText(textContent);
    	for(String userName : list) {
    		User userToTag = userRepository.findByUserName(userName);
    		if(user != null) {
    			notificationRepository.save(new Notification(userToTag.getUserId(), postId, "comment-mention", "You were mentioned in a comment on a post.", true));
    		}
    	}
        return comment;
    }

    @DeleteMapping("/comments")
    public boolean delete(@RequestBody Map<String, String> body){
        int commentId = Integer.parseInt(body.get("commentId"));
        commentRepository.delete(commentId);
        return true;
    }


}