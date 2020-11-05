
package hive.app.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/")
    public String thisIsForTesting(){
        return "Communicating to THE REPO5...";
    }
    
    @GetMapping("/posts")
    public List<Post> findAll(){
        return postService.findAll();
    }

    @GetMapping("/posts/byPostId/{id}")
    public Post findByPostId(@PathVariable String id){
        return postService.findByPostId(id);
    }
    
    @GetMapping("/posts/byHiveId/{hiveId}")
    public List<Post> findByHiveId(@PathVariable String hiveId){
        return postService.findByHiveId(hiveId); 
    }
    
    @GetMapping("/posts/byUserId/{userId}")
    public List<Post> findByUserId(@PathVariable String userId){
        return postService.findByUserId(userId); 
    }

    @PostMapping("/posts")
    public Post create(@RequestBody Map<String, String> body){
        return postService.create(body);
    }

    @PutMapping("/posts")
    public Post update(@RequestBody Map<String, String> body){
    	return postService.update(body);
    }

    @DeleteMapping("/posts")
    public boolean delete(@RequestBody Map<String, String> body){
    	return postService.delete(body);
    }
}