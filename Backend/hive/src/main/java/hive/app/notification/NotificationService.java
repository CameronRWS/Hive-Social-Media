
package hive.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hive.app.websocket.WebSocketServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    
    public List<Notification> findAll(){
        return notificationRepository.findAll();
    }
    
    public Notification findByNotiId(String id){
        int notiId = Integer.parseInt(id);
        return notificationRepository.findOne(notiId);
    }
    
    public List<Notification> findByUserId(String userId){
        int theUserId = Integer.parseInt(userId);
        return notificationRepository.findByUserId(theUserId); 
    }
    
    public Map<String, Object> getNotiCount(String userId){
        int theUserId = Integer.parseInt(userId);
        int notiCount = notificationRepository.findNewByUserId(theUserId).size();
        Map<String, Object> map = new HashMap<>();
        map.put("newNotiCount", notiCount);
        return map;
    }
    
    public Integer getNotiCountAsInt(String userId){
        int theUserId = Integer.parseInt(userId);
        return notificationRepository.findNewByUserId(theUserId).size();
    }
    
    public Notification read(String id){
        int notiId = Integer.parseInt(id);
        Notification noti = notificationRepository.findOne(notiId);
        noti.setIsNew(false);
        notificationRepository.save(noti);
        try {
			WebSocketServer.sendUpdatedNotiCount(noti.getOwnerUserId());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return noti;
    }
}