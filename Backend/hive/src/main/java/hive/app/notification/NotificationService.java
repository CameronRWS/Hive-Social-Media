
package hive.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        int notiCount = notificationRepository.findByUserId(theUserId).size();
        Map<String, Object> map = new HashMap<>();
        map.put("notiCount", notiCount);
        return map;
    }
    
    public Notification read(String id){
        int notiId = Integer.parseInt(id);
        Notification noti = notificationRepository.findOne(notiId);
        noti.setIsNew(false);
        return notificationRepository.save(noti);
    }
}