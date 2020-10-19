package hive.app.postTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.notification.NotificationRepository;
import hive.app.post.Post;
import hive.app.post.PostRepository;
import hive.app.post.PostService;
import hive.app.user.User;
import hive.app.user.UserRepository;
import hive.app.utils.DateTime;
import hive.app.utils.Regex;

@RunWith(SpringRunner.class)
public class PostServiceTest {
	
	@TestConfiguration
	static class test {
		@Bean
		public PostService pService() {
			return new PostService();
		}
		
		@Bean
		public PostRepository getPostRepo() {
			return mock(PostRepository.class);
		}
		
		@Bean
		public UserRepository getUserRepo() {
			return mock(UserRepository.class);
		}
		
		@Bean
		public HiveRepository getHiveRepo() {
			return mock(HiveRepository.class);
		}
		
		@Bean
		public NotificationRepository getNotiRepo() {
			return mock(NotificationRepository.class);
		}
		
		@Bean
		public MemberRepository getMemberRepo() {
			return mock(MemberRepository.class);
		}
	}
	
	@Autowired
	private PostService ps;
	
	@Autowired
	private PostRepository pr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private HiveRepository hr;
	
	@Autowired
	private NotificationRepository nr;

	Map<String, String> body;
	
	@Before
	public void setup() {
		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("title", "post title test @CameronX @AnotherGuy @NewGuy");
		body.put("textContent", "post content test @CameronX @TestUser @NewGuy");
		
		Hive testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		User testUser = new User("userName", "displayName", "birthday", "biography", "location");
//		Post testPost = new Post(Integer.getInteger(body.get("hiveId")), testUser, body.get("title"), body.get("textContent"));
		when(hr.findOne(3)).thenReturn(testHive);
		when(ur.findOne(1)).thenReturn(testUser);
//		when(pr.save((Post)any(Post.class))).thenReturn(new Post(Integer.getInteger(body.get("hiveId")), testUser, body.get("title"), body.get("textContent")));
	}
	
	@Test
	public void testCreate() {
//		Post post = ps.create(body);
//		System.out.println(post);
//		assertEquals(3, post.getHiveId());
		assertEquals(true, "post title test @CameronX @AnotherGuy @NewGuy".equals(body.get("title")));
		assertEquals(true, "post content test @CameronX @TestUser @NewGuy".equals(body.get("textContent")));
	}
	
	@Test
	public void testRegex() {
		@SuppressWarnings("serial")
		List<String> usersCorrect = new ArrayList<String>() {{
				add("CameronX");
				add("AnotherGuy");
				add("NewGuy");
			}
		};
		List<String> usersTest = Regex.getUserNamesMentionedInText(body.get("title"));
		boolean isCorrect = true;
		for(int i = 0; i < usersTest.size(); i++) {
			try {
				if(!usersCorrect.get(i).equals(usersTest.get(i))) {
					isCorrect = false;
					break;
				}
			} catch (Exception x) {
				isCorrect = false;
			}
		}
		assertEquals(true, isCorrect);
	}
	
	
}