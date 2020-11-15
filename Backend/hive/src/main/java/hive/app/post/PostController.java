
package hive.app.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Post entity")
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @ApiOperation(value="This is for testing, you can safely ignore")
    @GetMapping("/")
    public String thisIsForTesting(){
        return "Communicating to THE REPO5...";
    }
    
    @ApiOperation(value="Get all posts")
    @GetMapping("/posts")
    public List<Post> findAll(){
        return postService.findAll();
    }

    @ApiOperation(value="Get a post by it's postId")
    @GetMapping("/posts/byPostId/{id}")
    public Post findByPostId(@PathVariable String id){
        return postService.findByPostId(id);
    }
    
    @ApiOperation(value="Get all posts in a hive")
    @GetMapping("/posts/byHiveId/{hiveId}")
    public List<Post> findByHiveId(@PathVariable String hiveId){
        return postService.findByHiveId(hiveId); 
    }
    
    @ApiOperation(value="Get all posts posted by a user")
    @GetMapping("/posts/byUserId/{userId}")
    public List<Post> findByUserId(@PathVariable String userId){
        return postService.findByUserId(userId); 
    }

    @ApiOperation(value="Create a post")
    @PostMapping("/posts")
    public Post create(@RequestBody Map<String, String> body){
        return postService.create(body);
    }

    @ApiOperation(value="Update a post")
    @PutMapping("/posts")
    public Post update(@RequestBody Map<String, String> body){
    	return postService.update(body);
    }

    @ApiOperation(value="Delete a post")
    @DeleteMapping("/posts")
    public boolean delete(@RequestBody Map<String, String> body){
    	return postService.delete(body);
    }
}