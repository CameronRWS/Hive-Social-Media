
package hive.hive;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c WHERE c.postId = :postId")
    List<Comment> findByPostId(@Param("postId") int postId);
    
    @Query("SELECT c FROM Comment c WHERE c.userId = :userId")
    List<Comment> findByUserId(@Param("userId") int userId);
}
