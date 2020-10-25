package hive.app.requestTests;

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
import hive.app.notification.NotificationService;
import hive.app.post.Post;
import hive.app.postTests.PostServiceTest;
import hive.app.request.Request;
import hive.app.request.RequestRepository;
import hive.app.request.RequestService;
import hive.app.user.User;
import hive.app.user.UserRepository;

@RunWith(SpringRunner.class)
public class RequestServiceTest {

	@TestConfiguration
	static class test {
		@Bean
		public RequestService rService() {
			return new RequestService();
		}
		
		@Bean
		public RequestRepository rRepository() {
			return mock(RequestRepository.class);
		}
		
		@Bean
		public UserRepository uRepository() {
			return mock(UserRepository.class);
		}
		
		@Bean
		public HiveRepository hRepository() {
			return mock(HiveRepository.class);
		}
		
		@Bean
		public NotificationRepository nRepository() {
			return mock(NotificationRepository.class);
		}
		
		@Bean
		public MemberRepository mRepository() {
			return mock(MemberRepository.class);
		}
	}
	
	@Autowired
	private RequestService rs;
	
	@Autowired
	private RequestRepository rr;
	
	@Autowired 
	private UserRepository ur;
	
	@Autowired 
	private HiveRepository hr;
	
	@Autowired
	private NotificationRepository nr;
	
	@Autowired
	private MemberRepository mr;
	
	public ArrayList<String> users;
	public ArrayList<String> members;
	public Map<String, String> body;
	public Hive testHive;
	public User testUser;
	
	@Before 
	public void beforeTests() {
		when(mr.findOne((MemberIdentity)any(MemberIdentity.class))).thenAnswer(new Answer<Member>() {
		    public Member answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	if(args[0] == null) {
		    		return null;
		    	}
		    	MemberIdentity mi = (MemberIdentity) args[0];
		    	if(members.contains(mi.getUser().getUserName())) {
		    		return new Member(mi, false);
		    	} else {
		    		return null;
		    	}
		    }
		});
		when(ur.findByUserName((String)any(String.class))).thenAnswer(new Answer<User>() {
		    public User answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	if(args[0] == null) {
		    		return null;
		    	}
		    	String userName = (String) args[0];
		    	if(users.contains(userName)) {
		    		return new User(userName, "displayName", "birthday", "biography", "location");
		    	} else {
		    		return null;
		    	}
		    }
		});
		
		when(rr.save((Request)any(Request.class))).thenAnswer(new Answer<Request>() {
		    public Request answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	return (Request) args[0];
		    }
		});
		
		when(nr.save((Notification)any(Notification.class))).thenAnswer(new Answer<Notification>() {
		    public Notification answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	Notification notification = (Notification) args[0];
		    	return notification;
		    }
		});	
	}
	// first test, no mocking yet.
	@Test
	public void testFindByUserId() {
		int testUserId = 1;
		List<Request> requests = rs.findByUserId(String.valueOf(testUserId));
		boolean correctUserId = true;
		for (Request request : requests) {
			if (request.getUser().getUserId() != testUserId) {
				correctUserId = false;
				break;
			}
		}
		assertEquals(true, correctUserId);
	}
	
	@Test
	public void testCreate() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("Killua", "displayName", "birthday", "biography", "location");
		
		when(hr.findOne((Integer) any(Integer.class))).thenReturn(this.testHive);
		when(ur.findOne((Integer) any(Integer.class))).thenReturn(this.testUser);
		
		body = new HashMap<String, String>();
		body.put("hiveId", "3");
		body.put("userId", "1");
		body.put("requestMessage", "Can I join the hive??");
		
		users = new ArrayList<String>();
		users.add("Killua");
		
		rs.create(body);
		verify(rr, times(1)).save((Request) any(Request.class));
		resetMocked();
	}
	
	public void resetMocked() {
		reset(rr);
		reset(nr);
		reset(ur);
	}
	
}
