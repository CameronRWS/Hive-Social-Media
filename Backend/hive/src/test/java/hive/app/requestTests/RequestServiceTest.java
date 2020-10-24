package hive.app.requestTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import hive.app.hive.HiveRepository;
import hive.app.member.MemberRepository;
import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.notification.NotificationService;
import hive.app.postTests.PostServiceTest;
import hive.app.request.Request;
import hive.app.request.RequestRepository;
import hive.app.request.RequestService;
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
}
