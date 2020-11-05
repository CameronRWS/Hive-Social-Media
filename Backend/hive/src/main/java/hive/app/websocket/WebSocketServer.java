package hive.app.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hive.app.notification.NotificationService;

@ServerEndpoint("/websocket/{userId}")
@Component
public class WebSocketServer {
	
    private static NotificationService notiService;
    
    @Autowired
    public void setNotificationRepo(NotificationService ns) {
    	notiService = ns;
    }
	
    private static Map<Integer, Session> userSessions = new HashMap<Integer, Session>();
    private static Map<Session, Integer> sessionUsers = new HashMap<Session, Integer>();

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") int userId) throws IOException {
		addUserToWebSocket(session, userId);
		updateNotiCount(session, userId);
		updateAllUsersOnlineCount();
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		Integer userId = sessionUsers.get(session);
		removeUserFromWebSocket(session, userId);
		updateAllUsersOnlineCount();
	}
	
	public static void addUserToWebSocket(Session session, int userId) {
		System.out.println("userId: " + userId + " added");
		userSessions.put(userId, session);
		sessionUsers.put(session, userId);
	}
	
	public static void removeUserFromWebSocket(Session session, int userId) {
		System.out.println("userId: " + userId + " removed");
		userSessions.remove(userId);
		sessionUsers.remove(session);
	}
	
	public static void updateAllUsersOnlineCount() {
		userSessions.forEach((userIdToSendTo, theSession) -> {
			try {
				theSession.getBasicRemote().sendText("onlineUsers: " + userSessions.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public static void updateNotiCount(Session session, int userId) throws IOException  {
		Integer notiCount = notiService.getNotiCountAsInt(userId+"");
		session.getBasicRemote().sendText("newNotifications: " + notiCount);
	}
	
	public static void sendUpdatedNotiCount(int userId) throws IOException {
		System.out.println("Got here!");
		if(userSessions.containsKey(userId)) {
			Integer notiCount = notiService.getNotiCountAsInt(userId+"");
			Session session = userSessions.get(userId);
			session.getBasicRemote().sendText("newNotifications: " + notiCount);
		}
	}
}
