package hive.app.notification;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    
    @Query("SELECT p FROM Notification p WHERE p.ownerUserId = :userId")
    List<Notification> findByUserId(@Param("userId") int userId);

}
