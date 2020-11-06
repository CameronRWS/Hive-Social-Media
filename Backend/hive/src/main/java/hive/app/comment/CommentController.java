
package hive.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;
    
    @GetMapping("/comments")
    public List<Comment> findAll(){
        return commentService.findAll();
    }

    @GetMapping("/comments/commentId/{id}")
    public Comment show(@PathVariable String id){
        return commentService.findByCommentId(id);
    }
    
    @GetMapping("/comments/byPostId/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable String postId){
    	return commentService.findByPostId(postId);
    }

    @PostMapping("/comments")
    public Comment create(@RequestBody Map<String, String> body){
    	return commentService.create(body);
    }

    @DeleteMapping("/comments")
    public boolean delete(@RequestBody Map<String, String> body){
    	return commentService.delete(body);
    }
}