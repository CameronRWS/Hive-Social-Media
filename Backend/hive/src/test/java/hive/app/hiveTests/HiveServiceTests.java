package hive.app.hiveTests;

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
import hive.app.hive.HiveService;
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

@RunWith(SpringRunner.class)
public class HiveServiceTests {
	
	@TestConfiguration
	static class test {
		
		@Bean 
		public HiveService hService() {
			return new HiveService();
		}
		
		@Bean
		public HiveRepository getHiveRepo() {
			return mock(HiveRepository.class);
		}
		
	}

	@Autowired
	private HiveService hs;
	
	@Autowired
	private HiveRepository hr;
	
	@Before
	public void beforeTests() {
		when(hr.save((Hive)any(Hive.class))).thenAnswer(new Answer<Hive>() {
		    public Hive answer(InvocationOnMock invocation) throws Throwable {
		    	Object[] args = invocation.getArguments();
		    	return (Hive) args[0];
		    }
		});
	}
	
	
	// test for adding multiple interests??
	
	@Test
	public void testCreate() {
		
	}
}
