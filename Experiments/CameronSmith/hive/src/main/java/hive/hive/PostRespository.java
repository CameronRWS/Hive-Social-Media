
package hive.hive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface PostRespository extends JpaRepository<Post, Integer> {

    // custom query to search to post by it's textContent
//	@Query("SELECT p FROM post p WHERE p.textContent LIKE '%:textContent%'")
//    public List<Post> findByTextContentContaining(@Param("textContent") String textContent);
//	
//	@Query("SELECT p FROM post p WHERE p.hiveId LIKE '%:textContent%'")
//    public List<Post> findByTextContentContaining(@Param("textContent") String textContent);
//	
//	public static final EntityManager entityManager = null;
//
//	
//    public List<Post> getPostsInAHive(String hiveId) {
//        Query query = entityManager.createNativeQuery("SELECT em.* FROM spring_data_jpa_example.employee as em " +
//                "WHERE em.firstname LIKE ?", Employee.class);
//        query.setParameter(1, firstName + "%");
//        return query.getResultList();
//    }
    
    @Query("SELECT p FROM Post p WHERE p.hiveId = :hiveId")
    List<Post> findByHiveId(@Param("hiveId") int hiveId);

}
