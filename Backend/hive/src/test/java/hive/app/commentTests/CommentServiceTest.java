package hive.app.commentTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import hive.app.comment.Comment;
import hive.app.comment.CommentRepository;
import hive.app.comment.CommentService;
import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.post.Post;
import hive.app.post.PostRepository;
import hive.app.user.User;
import hive.app.user.UserRepository;
import hive.app.utils.Regex;

@RunWith(SpringRunner.class)
public class CommentServiceTest {
	
	@TestConfiguration
	static class test {
		@Bean
		public CommentService cService() {
			return new CommentService();
		}
		
		@Bean
		public CommentRepository getCommentRepo() {
			return mock(CommentRepository.class);
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
	private CommentService cs;
	
	@Autowired
	private CommentRepository cr;
	
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
		
		when(cr.save((Comment)any(Comment.class))).thenAnswer(new Answer<Comment>() {
		    public Comment answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	return (Comment) args[0];
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
		testPost = new Post(3, this.testUser, "oldTitle", "oldContent");
		when(hr.findOne((Integer)any(Integer.class))).thenReturn(this.testHive);
		when(ur.findOne((Integer)any(Integer.class))).thenReturn(this.testUser);
		when(pr.findOne((Integer)any(Integer.class))).thenReturn(this.testPost);
		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("postId", "2");
		body.put("textContent", "post content test @Cameron @NewGuy @NotARealUser");
		realUsers = new ArrayList<String>();
		realUsers.add("Cameron");
		realUsers.add("NotApartOfHive");
		realUsers.add("NewGuy");
		realMembers = new ArrayList<String>();
		realMembers.add("Cameron");
		realMembers.add("NewGuy");
		
		cs.create(body);
		//check 1 comment was created;
		verify(cr, times(1)).save((Comment)any(Comment.class));	
		//check 1 notification was created;
		verify(nr, times(1)).save((Notification)any(Notification.class));	
		//check that only 2 users where checked for (since Cameron is the owner of the post);
		verify(ur, times(2)).findByUserName((String)any(String.class));	
		//check the 2 users where checked for their existence
		verify(ur).findByUserName("NewGuy");
		verify(ur).findByUserName("NotARealUser");
		resetMocked();
	}
	
	@Test
	public void testCreate_noSelfTag() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("AnotherGuy", "displayName", "birthday", "biography", "location");
		testPost = new Post(3, this.testUser, "oldTitle", "oldContent");
		when(hr.findOne((Integer)any(Integer.class))).thenReturn(this.testHive);
		when(ur.findOne((Integer)any(Integer.class))).thenReturn(this.testUser);
		when(pr.findOne((Integer)any(Integer.class))).thenReturn(this.testPost);
		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("postId", "2");
		body.put("textContent", "post content test @Cameron @NewGuy @NotARealUser");
		realUsers = new ArrayList<String>();
		realUsers.add("Cameron");
		realUsers.add("NotApartOfHive");
		realUsers.add("NewGuy");
		realUsers.add("AnotherGuy");
		realMembers = new ArrayList<String>();
		realMembers.add("Cameron");
		realMembers.add("NewGuy");
		realMembers.add("AnotherGuy");
		
		cs.create(body);
		//check 1 comment was created;
		verify(cr, times(1)).save((Comment)any(Comment.class));	
		//check 2 notifications were created;
		verify(nr, times(2)).save((Notification)any(Notification.class));	
		//check that only 2 users where checked for (since Cameron is the owner of the post);
		verify(ur, times(3)).findByUserName((String)any(String.class));	
		//check the 2 users where checked for their existence
		verify(ur).findByUserName("NewGuy");
		verify(ur).findByUserName("NotARealUser");
		verify(ur).findByUserName("Cameron");
		resetMocked();
	}

	//resets interaction counts
	public void resetMocked() {
		reset(cr);
		reset(nr);
		reset(ur);
	}
	
	
}