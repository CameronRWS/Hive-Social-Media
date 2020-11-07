
package hive.app.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Comment entity")
@RestController
public class CommentController {

    @Autowired
    CommentService commentService;
    
    @ApiOperation(value="Get all comments")
    @GetMapping("/comments")
    public List<Comment> findAll(){
        return commentService.findAll();
    }
    
    @ApiOperation(value="Get a comment by it's commentId")
    @GetMapping("/comments/commentId/{id}")
    public Comment show(@PathVariable String id){
        return commentService.findByCommentId(id);
    }
    
    @ApiOperation(value="Get all comments by it's postId")
    @GetMapping("/comments/byPostId/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable String postId){
    	return commentService.findByPostId(postId);
    }

    @ApiOperation(value="Create a comment")
    @PostMapping("/comments")
    public Comment create(@RequestBody Map<String, String> body){
    	return commentService.create(body);
    }

    @ApiOperation(value="Delete a comment")
    @DeleteMapping("/comments")
    public boolean delete(@RequestBody Map<String, String> body){
    	return commentService.delete(body);
    }
}