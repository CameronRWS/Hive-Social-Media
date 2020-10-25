package hive.app.postTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
import hive.app.notification.Notification;
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
	
	@Autowired
	private MemberRepository mr;
	
	public ArrayList<String> realUsers;
	public ArrayList<String> realMembers;
	public Map<String, String> body;
	public Hive testHive;
	public User testUser;
	public Post testPost;

	@Before
	public void beforeTests() {
		when(mr.findOne((MemberIdentity)any(MemberIdentity.class))).thenAnswer(new Answer<Member>() {
		    public Member answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	if(args[0] == null) {
		    		return null;
		    	}
		    	MemberIdentity mi = (MemberIdentity) args[0];
		    	if(realMembers.contains(mi.getUser().getUserName())) {
		    		return new Member(mi, false);
		    	} else {
		    		return null;
		    	}
		    }
		});
		
		when(pr.save((Post)any(Post.class))).thenAnswer(new Answer<Post>() {
		    public Post answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	return (Post) args[0];
		    }
		});
		
		when(ur.findByUserName((String)any(String.class))).thenAnswer(new Answer<User>() {
		    public User answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	if(args[0] == null) {
		    		return null;
		    	}
		    	String userName = (String) args[0];
		    	if(realUsers.contains(userName)) {
		    		return new User(userName, "displayName", "birthday", "biography", "location");
		    	} else {
		    		return null;
		    	}
		    }
		});
		
		when(nr.save((Notification)any(Notification.class))).thenAnswer(new Answer<Notification>() {
		    public Notification answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	Notification noti = (Notification) args[0];
		    	return noti;
		    }
		});	
	}
	
	@Test
	public void testCreate_hasSelfTag() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("Cameron", "displayName", "birthday", "biography", "location");
		when(hr.findOne((Integer)any(Integer.class))).thenReturn(this.testHive);
		when(ur.findOne((Integer)any(Integer.class))).thenReturn(this.testUser);
		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("title", "post title test @Cameron @NotApartOfHive @NewGuy");
		body.put("textContent", "post content test @Cameron @NewGuy @NotARealUser");
		realUsers = new ArrayList<String>();
		realUsers.add("Cameron");
		realUsers.add("NotApartOfHive");
		realUsers.add("NewGuy");
		realMembers = new ArrayList<String>();
		realMembers.add("Cameron");
		realMembers.add("NewGuy");
		
		ps.create(body);
		//check 1 post was created;
		verify(pr, times(1)).save((Post)any(Post.class));	
		//check 1 notification was created;
		verify(nr, times(1)).save((Notification)any(Notification.class));	
		//check that only 3 users where checked for (since Cameron is the owner of the post);
		verify(ur, times(3)).findByUserName((String)any(String.class));	
		//check the 3 users where checked for their existence
		verify(ur).findByUserName("NotApartOfHive");
		verify(ur).findByUserName("NewGuy");
		verify(ur).findByUserName("NotARealUser");
		resetMocked();
	}
	
	@Test
	public void testCreate_noSelfTag() {
		//In this test, a user is attempting to create a post that tags 4 unique people.
		//1 user doesn't exist so it shouldn't receive a notification
		//1 user isn't apart of the hive so it shouldn't receive a notification
		//Should result in 2 notifications
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("ThisGuy", "displayName", "birthday", "biography", "location");
		when(hr.findOne((Integer)any(Integer.class))).thenReturn(this.testHive);
		when(ur.findOne((Integer)any(Integer.class))).thenReturn(this.testUser);
		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("title", "post title test @Cameron @NotApartOfHive @NewGuy");
		body.put("textContent", "post content test @Cameron @NewGuy @NotARealUser");
		realUsers = new ArrayList<String>();
		realUsers.add("Cameron");
		realUsers.add("NotApartOfHive");
		realUsers.add("NewGuy");
		realUsers.add("ThisGuy");
		realMembers = new ArrayList<String>();
		realMembers.add("Cameron");
		realMembers.add("NewGuy");
		//method we are testing
		ps.create(body);
		//check 1 post was created;
		verify(pr, times(1)).save((Post)any(Post.class));	
		//check 2 notifications was created;
		verify(nr, times(2)).save((Notification)any(Notification.class));	
		//check that only 3 users where checked for (since Cameron is the owner of the post);
		verify(ur, times(4)).findByUserName((String)any(String.class));	
		//check the 4 users were checked for their existence
		verify(ur).findByUserName("NotApartOfHive");
		verify(ur).findByUserName("NewGuy");
		verify(ur).findByUserName("NotARealUser");
		verify(ur).findByUserName("Cameron");
		//reset
		resetMocked();
	}
	
	@Test
	public void testUpdate() {
		//prepare test
		testPost = new Post(3, this.testUser, "oldTitle", "oldContent");
		when(pr.findOne((Integer)any(Integer.class))).thenReturn(this.testPost);
		Map<String, String> body = new HashMap<String, String>();
		body.put("postId", "0");
		body.put("title", "newTitle");
		body.put("textContent", "newContent");
		//method we are testing
		Post updatedPost = ps.update(body);
		//tests
		assertEquals(true, updatedPost.getTitle().equals(body.get("title")));
		assertEquals(true, updatedPost.getTextContent().equals(body.get("textContent")));
		//reset
		resetMocked();
	}

	@Test
	public void testRegex() {
		Map<String, String> body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("title", "post title test @Cameron @NotApartOfHive @NewGuy");
		body.put("textContent", "post content test @Cameron @NewGuy @NotARealUser");
		
		@SuppressWarnings("serial")
		List<String> usersCorrect = new ArrayList<String>() {{
				add("Cameron");
				add("NotApartOfHive");
				add("NewGuy");
				add("NotARealUser");
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
	
	//resets interaction counts
	public void resetMocked() {
		reset(pr);
		reset(nr);
		reset(ur);
	}
	
	
}