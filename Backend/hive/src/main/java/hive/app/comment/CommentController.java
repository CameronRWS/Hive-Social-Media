
package hive.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.post.Post;
import hive.app.post.PostRepository;
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
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HiveRepository hiveRepository;
    @Autowired
    PostRepository postRepository;
    
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
        Post post = postRepository.findOne(postId);
        Hive hive = hiveRepository.findOne(post.getHiveId());
        String textContent = body.get("textContent");
        if(post.getUser().getUserId() != user.getUserId()) {
        	notificationRepository.save(new Notification(post.getUser().getUserId(), user.getUserId(), postId, "post-commentReceived"));
        }
        Comment comment = commentRepository.save(new Comment(postId, user, textContent));
        //create notification for all usernames found in comment
    	List<String> list = Regex.getUserNamesMentionedInText(textContent);
    	list.remove(user.getUserName());
    	for(String userName : list) {
    		User userToTag = userRepository.findByUserName(userName);
    		if(user != null) {
        		Member member = memberRepository.findOne(new MemberIdentity(hive, user));
        		if(member != null) {
        			notificationRepository.save(new Notification(userToTag.getUserId(), user.getUserId(), postId, "post-commentMention"));
        		}
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