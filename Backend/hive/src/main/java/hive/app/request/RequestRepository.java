package hive.app.request;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, RequestIdentity> {
	@Query("SELECT l FROM Request l WHERE l.requestIdentity.hiveId = :hiveId")
	List<Request> findByHiveId(@Param("hiveId") int hiveId);
	
	@Query("SELECT l FROM Request l WHERE l.requestIdentity.user.userId = :userId")
    List<Request> findByUserId(@Param("userId") int userId);
}
