
package hive.app.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    
    @Query("SELECT p FROM Post p WHERE p.hiveId = :hiveId")
    List<Post> findByHiveId(@Param("hiveId") int hiveId);
    
    @Query("SELECT p FROM Post p WHERE p.user.userId = :userId")
    List<Post> findByUserId(@Param("userId") int userId);

}
