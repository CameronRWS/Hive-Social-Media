package hive.app.notificationTests;

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

import hive.app.notification.Notification;
import hive.app.notification.NotificationRepository;
import hive.app.notification.NotificationService;
import hive.app.postTests.PostServiceTest;

@RunWith(SpringRunner.class)
public class NotificationServiceTest {
	
	@TestConfiguration
	static class test {
		@Bean
		public NotificationService nService() {
			return new NotificationService();
		}
		
		@Bean
		public NotificationRepository getRepo() {
			return mock(NotificationRepository.class);
		}
	}
	
	@Autowired
	private NotificationService ns;

	
	//Cameron first test, doesn't mock... mainly done to learn.
	@Test
	public void testFindByUserId() {
		int userIdToTest = 1;
		List<Notification> list = ns.findByUserId(String.valueOf(userIdToTest));
		boolean isCorrectUserId = true;
		for(Notification noti : list) {
			if(noti.getOwnerUserId() != userIdToTest) {
				isCorrectUserId = false;
				break;
			}
		}
		assertEquals(true, isCorrectUserId);
	}
	
}