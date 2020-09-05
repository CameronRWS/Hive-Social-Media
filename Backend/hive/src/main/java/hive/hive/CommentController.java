
package hive.hive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    
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
        return commentRepository.save(new Comment(postId, user, textContent));
    }

    @PutMapping("/comments")
    public Comment update(@RequestBody Map<String, String> body){
        int commentId = Integer.parseInt(body.get("commentId"));
        String textContent = body.get("textContent");
        Comment comment = commentRepository.findOne(commentId);
        comment.setTextContent(textContent);
        return commentRepository.save(comment);
    }

    @DeleteMapping("/comments")
    public boolean delete(@RequestBody Map<String, String> body){
        int commentId = Integer.parseInt(body.get("commentId"));
        commentRepository.delete(commentId);
        return true;
    }


}