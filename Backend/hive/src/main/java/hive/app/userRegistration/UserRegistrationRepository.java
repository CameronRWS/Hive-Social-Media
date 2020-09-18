
package hive.app.userRegistration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration, Integer> {

    @Query("SELECT l FROM UserRegistration l WHERE l.userRegistrationIdentity.user.userId= :userId")
    UserRegistration findByUserId(@Param("userId") int userId);
    
    @Query("SELECT l FROM UserRegistration l WHERE l.userRegistrationIdentity.user.userName= :userName")
    UserRegistration findByUserName(@Param("userName") String userName);
    
    @Query("SELECT l FROM UserRegistration l WHERE l.email LIKE :email")
    UserRegistration findByEmail(@Param("email") String email);
}
