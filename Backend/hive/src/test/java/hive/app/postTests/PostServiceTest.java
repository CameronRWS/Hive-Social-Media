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
	
//	@SuppressWarnings("serial")
	
	@Before
	public void setup() {
		when(hr.findOne(3)).thenReturn(new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20")));
		when(ur.findOne(1)).thenReturn(new User("userName", "displayName", "birthday", "biography", "location"));
//		when(pr.save(new Post(new Post("3", new User("userName", "displayName", "birthday", "biography", "location"), "title", "textContent")))).thenReturn(new Post("3", new User("userName", "displayName", "birthday", "biography", "location"), "title", "textContent"));
//		when(pr.save((Post)any(Post.class))).thenAnswer(x -> {
//			Post p = x.getArgument(0);
//			return null;
//		});

		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("title", "post title test @CameronX @AnotherGuy @NewGuy");
		body.put("textContent", "post content test @CameronX @TestUser @NewGuy");
	}
	
//	@Test
//	public void testCreate() {
//		Post post = ps.create(body);
//		assertEquals(3, post.getHiveId());
////		assertEquals(1, post.getUser().getUserId());
////		assertEquals("post title test @CameronX @AnotherGuy @NewGuy", post.getTitle());
////		assertEquals("post content test @CameronX @TestUser @NewGuy", post.getTextContent());
//		
//	}
	
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
	
//	public Post create(Map<String, String> body){
//		
//        int hiveId = Integer.parseInt(body.get("hiveId"));
//        int userId = Integer.parseInt(body.get("userId"));
//        User user = userRepository.findOne(userId);
//        Hive hive = hiveRepository.findOne(hiveId);
//        String title = body.get("title");
//        String textContent = body.get("textContent");
//        Post post = postRepository.save(new Post(hiveId, user, title, textContent));
//    	List<String> list = Regex.getUserNamesMentionedInText(title);
//    	List<String> listBody = Regex.getUserNamesMentionedInText(textContent);
//    	//add the two lists together as a set
//    	for(String userName : listBody) {
//    		if(list.contains(userName) != true) {
//    			list.add(userName);
//    		}
//    	}
//        //create notification for all tagged users
//    	list.remove(user.getUserName());
//    	for(String userName : list) {
//    		User userToTag = userRepository.findByUserName(userName);
//    		if(user != null) {
//        		Member member = memberRepository.findOne(new MemberIdentity(hive, userToTag));
//        		if(member != null) {
//        			notificationRepository.save(new Notification(userToTag.getUserId(), user.getUserId(), post.getPostId(), "post-postMention"));
//        		}
//    		}
//    	}
//        return post;
//    }
	
	
}