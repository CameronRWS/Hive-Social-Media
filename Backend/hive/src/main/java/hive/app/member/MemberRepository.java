
package hive.app.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberIdentity> {
    @Query("SELECT l FROM Member l WHERE l.memberIdentity.hive.hiveId= :hiveId")
    List<Member> findByHiveId(@Param("hiveId") int hiveId);
    
    @Query("SELECT l FROM Member l WHERE l.memberIdentity.hive.hiveId= :hiveId " +
    "AND l.isModerator = true")
    List<Member> findByHiveIdAndIsMod(@Param("hiveId") int hiveId);
    
    @Query("SELECT l FROM Member l WHERE l.memberIdentity.user.userId= :userId")
    List<Member> findByUserId(@Param("userId") int userId);

}
