
package hive.app.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;
import hive.app.utils.Regex;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    HiveRepository hiveRepository;

    @GetMapping("/")
    public String index2(){
        return "Communicating to THE REPO4...";
    }
    
    @GetMapping("/posts")
    public List<Post> index(){
        return postRepository.findAll();
    }

    @GetMapping("/posts/byPostId/{id}")
    public Post show(@PathVariable String id){
        int PostId = Integer.parseInt(id);
        return postRepository.findOne(PostId);
    }
    
    @GetMapping("/posts/byHiveId/{hiveId}")
    public List<Post> getPostsByHiveId(@PathVariable String hiveId){
        int theHiveId = Integer.parseInt(hiveId);
        return postRepository.findByHiveId(theHiveId); 
    }
    
    @GetMapping("/posts/byUserId/{userId}")
    public List<Post> getPostsByUserId(@PathVariable String userId){
        int theUserId = Integer.parseInt(userId);
        return postRepository.findByUserId(theUserId); 
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Map<String, String> body){
        int hiveId = Integer.parseInt(body.get("hiveId"));
        int userId = Integer.parseInt(body.get("userId"));
        User user = userRepository.findOne(userId);
        Hive hive = hiveRepository.findOne(hiveId);
        String title = body.get("title");
        String textContent = body.get("textContent");
        Post post = postRepository.save(new Post(hiveId, user, title, textContent));
    	List<String> list = Regex.getUserNamesMentionedInText(title);
    	List<String> listBody = Regex.getUserNamesMentionedInText(textContent);
    	//add the two lists together as a set
    	for(String userName : listBody) {
    		if(list.contains(userName) != true) {
    			list.add(userName);
    		}
    	}
        //create notification for all tagged users
    	list.remove(user.getUserName());
    	for(String userName : list) {
    		User userToTag = userRepository.findByUserName(userName);
    		if(user != null) {
        		Member member = memberRepository.findOne(new MemberIdentity(hive, userToTag));
        		if(member != null) {
        			notificationRepository.save(new Notification(userToTag.getUserId(), user.getUserId(), post.getPostId(), "post-postMention"));
        		}
    		}
    	}
        return post;
    }

    @PutMapping("/posts")
    public Post update(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        String title = body.get("title");
        String textContent = body.get("textContent");
        Post Post = postRepository.findOne(postId);
        Post.setTitle(title);
        Post.setTextContent(textContent);
        return postRepository.save(Post);
    }

    @DeleteMapping("/posts")
    public boolean delete(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        postRepository.delete(postId);
        return true;
    }
}