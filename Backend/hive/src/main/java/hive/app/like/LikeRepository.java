
package hive.app.like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeIdentity> {
    @Query("SELECT l FROM Like l WHERE l.likeIdentity.postId = :postId")
    List<Like> findByPostId(@Param("postId") int postId);
    
    @Query("SELECT l FROM Like l WHERE l.likeIdentity.user.userId = :userId")
    List<Like> findByUserId(@Param("userId") int userId);
}
