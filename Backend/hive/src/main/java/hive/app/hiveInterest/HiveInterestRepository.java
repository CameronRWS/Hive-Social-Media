
package hive.app.hiveInterest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveInterestRepository extends JpaRepository<HiveInterest, HiveInterestIdentity> {
    @Query("SELECT l FROM HiveInterest l WHERE l.hiveInterestIdentity.hiveId = :hiveId")
    List<HiveInterest> findByHiveId(@Param("hiveId") int hiveId);
    
    @Query("SELECT l FROM HiveInterest l WHERE l.hiveInterestIdentity.interestId = :interestId")
    List<HiveInterest> findByInterestId(@Param("interestId") int interestId);
}
