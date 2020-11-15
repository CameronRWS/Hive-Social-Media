package hive.app.memberTests;

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
import hive.app.hive.Hive;
import hive.app.hive.HiveRepository;
import hive.app.member.Member;
import hive.app.member.MemberIdentity;
import hive.app.member.MemberRepository;
import hive.app.member.MemberService;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.request.Request;
import hive.app.request.RequestIdentity;
import hive.app.request.RequestRepository;
import hive.app.request.RequestService;
import hive.app.user.User;
import hive.app.user.UserRepository;

@RunWith(SpringRunner.class)
public class MemberServiceTest {
	
	@TestConfiguration
	static class test {
		
		@Bean
		public MemberService sService() {
			return new MemberService();
		}
		
		@Bean
		public MemberRepository mRepository() {
			return mock(MemberRepository.class);
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
	}

	@Autowired
	private MemberService ms;
	
	@Autowired
	private MemberRepository mr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private HiveRepository hr;
	
	@Autowired
	private NotificationRepository nr;
	
	public Hive testHive;
	public User testUser;
	public HashMap<String, String> body;
	public Member testMember;
	
	@Before
	public void beforeTests() {
		when(mr.save((Member)any(Member.class))).thenAnswer(new Answer<Member>() {
		    public Member answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	return (Member) args[0];
		    }
		});
	}
	
	@Test
	public void testCreate() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("Casper", "displayName", "birthday", "biography", "location");
		
		when(hr.findOne((Integer) any(Integer.class))).thenReturn(this.testHive);
		when(ur.findOne((Integer) any(Integer.class))).thenReturn(this.testUser);
	
		body = new HashMap<String, String>();
		body.put("hiveId", "1");
		body.put("userId", "2");
		
		ms.create(body);
		
	// check for created member
		verify(mr, times(1)).save((Member) any(Member.class));
		
		resetMocked();
	}
	
	@Test
	public void testUpdate_rolePromotion() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("Lauren", "displayName", "birthday", "biography", "location");
		MemberIdentity memberIdentity = new MemberIdentity(testHive, testUser);
		testMember = new Member(memberIdentity, false);
		
		when(ur.findOne((Integer) any(Integer.class))).thenReturn(this.testUser);
		when(hr.findOne((Integer) any(Integer.class))).thenReturn(testHive);
		when(mr.findOne((MemberIdentity) any(MemberIdentity.class))).thenReturn(testMember);
		
		Map<String, String> body = new HashMap<String, String>();
		body.put("hiveId", "0");
		body.put("userId", "0");
		body.put("isModerator", "true");
		
		Member updatedMember = ms.update(body);
		
	//check Member was updated
		assertEquals(true, updatedMember.getIsModerator());
	//check notification was created
		verify(nr, times(1)).save((Notification) any(Notification.class));
		resetMocked();
	}
	
	@Test
	public void testUpdate_roleDemotion() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("Lila", "displayName", "birthday", "biography", "location");
		MemberIdentity memberIdentity = new MemberIdentity(testHive, testUser);
		testMember = new Member(memberIdentity, true);
		
		when(ur.findOne((Integer) any(Integer.class))).thenReturn(this.testUser);
		when(hr.findOne((Integer) any(Integer.class))).thenReturn(testHive);
		when(mr.findOne((MemberIdentity) any(MemberIdentity.class))).thenReturn(testMember);
		
		Map<String, String> body = new HashMap<String, String>();
		body.put("hiveId", "0");
		body.put("userId", "0");
		body.put("isModerator", "false");
		
		Member updatedMember = ms.update(body);
		
	//check Member was updated
		assertEquals(false, updatedMember.getIsModerator());
	//check notification was created
		verify(nr, times(1)).save((Notification) any(Notification.class));
		resetMocked();
	}
	
	@Test
	public void testUpdate_alreadyModerator() {
		testHive = new Hive("name", "description", "type", Double.valueOf("10"), Double.valueOf("20"));
		testUser = new User("Lila", "displayName", "birthday", "biography", "location");
		MemberIdentity memberIdentity = new MemberIdentity(testHive, testUser);
		testMember = new Member(memberIdentity, true);
		
		when(ur.findOne((Integer) any(Integer.class))).thenReturn(this.testUser);
		when(hr.findOne((Integer) any(Integer.class))).thenReturn(testHive);
		when(mr.findOne((MemberIdentity) any(MemberIdentity.class))).thenReturn(testMember);
		
		Map<String, String> body = new HashMap<String, String>();
		body.put("hiveId", "0");
		body.put("userId", "0");
		body.put("isModerator", "true");
		
		Member updatedMember = ms.update(body);
		
	//check Member stayed the same
		assertEquals(true, updatedMember.getIsModerator());
	//check notification was not created
		verify(nr, times(0)).save((Notification) any(Notification.class));
		resetMocked();
	}
	
	public void resetMocked() {
		reset(mr);
		reset(ur);
		reset(hr);
		reset(nr);
	}
}
