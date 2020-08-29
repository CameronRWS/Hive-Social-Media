
package hive.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    
    @GetMapping("/comments")
    public List<Comment> index(){
        return commentRepository.findAll();
    }

    @GetMapping("/comments/comment_id/{id}")
    public Comment show(@PathVariable String id){
        int commentId = Integer.parseInt(id);
        return commentRepository.findOne(commentId);
    }
    
    @GetMapping("/comments/post_id/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable String postId){
        int thePostId = Integer.parseInt(postId);
        return commentRepository.findByPostId(thePostId); 
    }
    
    @GetMapping("/comments/user_id/{userId}")
    public List<Comment> getCommentsByUserId(@PathVariable String userId){
        int theUserId = Integer.parseInt(userId);
        return commentRepository.findByUserId(theUserId); 
    }

    @PostMapping("/comments")
    public Comment create(@RequestBody Map<String, String> body){
        int postId = Integer.parseInt(body.get("postId"));
        int userId = Integer.parseInt(body.get("userId"));
        String textContent = body.get("textContent");
        return commentRepository.save(new Comment(postId, userId, textContent));
    }

    @PutMapping("/comments/{id}")
    public Comment update(@PathVariable String id, @RequestBody Map<String, String> body){
        int commentId = Integer.parseInt(id);
        Comment comment = commentRepository.findOne(commentId);
        comment.setTextContent(body.get("textContent"));
        return commentRepository.save(comment);
    }

    @DeleteMapping("comments/{id}")
    public boolean delete(@PathVariable String id){
        int commentId = Integer.parseInt(id);
        commentRepository.delete(commentId);
        return true;
    }


}