package hive.app.websocketTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import hive.app.notification.NotificationRepository;
import hive.app.notification.NotificationService;
import hive.app.websocket.WebSocketServer;

@RunWith(SpringRunner.class)
public class WebsocketTest {

	
	@TestConfiguration
	static class test {
		@Bean
		public WebSocketServer pService() {
			return new WebSocketServer();
		}
		
		@Bean
		public NotificationService getNoti() {
			return mock(NotificationService.class);
		}
		
		@Bean
		public NotificationRepository getNotiRepo() {
			return mock(NotificationRepository.class);
		}
		
		@Bean
		public Session getSession() {
			return mock(Session.class);
		}
		
		@Bean
		public Basic getBasic() {
			return mock(Basic.class);
		}
	}
	
	@Autowired
	private NotificationService ns;
	
	@Autowired
	private Session s;
	
	@Autowired
	private Basic b;
	
	@Autowired
	WebSocketServer ws;
	
	@Before
	public void beforeTests() throws IOException {		
		when(ns.getNotiCountAsInt((String)any(String.class))).thenAnswer(new Answer<Integer>() {
		    public Integer answer(InvocationOnMock invocation) throws Throwable {
		    	return 12;
		    }
		});
		
		when(s.getBasicRemote()).thenAnswer(new Answer<Basic>() {
		    public Basic answer(InvocationOnMock invocation) throws Throwable {
		    	return b;
		    }
		});
		
		Mockito.doNothing().when(b).sendText((String)any(String.class));
		
	}
		
	@Test
	public void testOnClose() throws IOException {
		//add user (tested in testOnOpen())
		ws.onOpen(s, 1);
		//check that the user has been added
		assertEquals(1, WebSocketServer.userSessions.size());
		//close this session
		ws.onClose(s);
		//make sure session is closed
		assertEquals(0, WebSocketServer.userSessions.size());
		//confirm only 2 messages were sent
		verify(b, times(2)).sendText((String)any(String.class));
		//reset Basic mock for onOpen()
		reset(b);
	}
	
	@Test
	public void testOnOpen() throws IOException {
		ws.onOpen(s, 1);
		//added 1 connection, should be 1
		assertEquals(1, WebSocketServer.userSessions.size());	
		//sent an updated noti count and updated online users count, so should be 2
		verify(b, times(2)).sendText((String)any(String.class));
		ws.onOpen(s, 2);
		//added a 2nd connection, should be 2
		assertEquals(2, WebSocketServer.userSessions.size());
		//send another set of 2 messages for the new user, then an updated online user count
		//for the first user so total messages sent should be 5.
		verify(b, times(5)).sendText((String)any(String.class));
		ws.onOpen(s, 1);
		//added a duplicate connection, should be 2
		assertEquals(2, WebSocketServer.userSessions.size());
	}
}
