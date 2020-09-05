
package hive.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

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
        String title = body.get("title");
        String textContent = body.get("textContent");
        return postRepository.save(new Post(hiveId, user, title, textContent));
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