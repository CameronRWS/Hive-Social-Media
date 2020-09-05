
package hive.app.hive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveRepository extends JpaRepository<Hive, Integer> {

	
	//'Select user.display_name, user.user_name, post.date_created, post.title, post.text_content From hive inner join post on hive.hive_id = post.hive_id left join user on post.user_id = user.user_id where hive.hive_id = 1;'
}
