
package hive.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import hive.app.websocket.WebSocketServer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

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
    
    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public Comment findByCommentId(String id){
        int commentId = Integer.parseInt(id);
        return commentRepository.findOne(commentId);
    }
    
    public List<Comment> findByPostId(String postId){
        int thePostId = Integer.parseInt(postId);
        return commentRepository.findByPostId(thePostId); 
    }

    public Comment create(Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        Post post = postRepository.findOne(postId);
        Hive hive = hiveRepository.findOne(post.getHiveId());
        String textContent = body.get("textContent");
        if(post.getUser().getUserId() != user.getUserId()) {
        	notificationRepository.save(new Notification(post.getUser().getUserId(), user.getUserId(), postId, "post-commentReceived"));
			try {
				WebSocketServer.sendUpdatedNotiCount(post.getUser().getUserId());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        Comment comment = commentRepository.save(new Comment(postId, user, textContent));
        //create notification for all usernames found in comment
    	List<String> list = Regex.getUserNamesMentionedInText(textContent);
    	list.remove(user.getUserName());
    	for(String userName : list) {
    		User userToTag = userRepository.findByUserName(userName);
    		if(userToTag != null) {
        		Member member = memberRepository.findOne(new MemberIdentity(hive, user));
        		if(member != null) {
        			notificationRepository.save(new Notification(userToTag.getUserId(), user.getUserId(), postId, "post-commentMention"));
        			try {
        				WebSocketServer.sendUpdatedNotiCount(userToTag.getUserId());
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		}
    		}
    	}
        return comment;
    }

    public boolean delete(Map<String, String> body){
        int commentId = Integer.parseInt(body.get("commentId"));
        commentRepository.delete(commentId);
        return true;
    }
}