
package hive.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;
    
    @GetMapping("/notifications")
    public List<Notification> index(){
        return notificationRepository.findAll();
    }

    @GetMapping("/notifications/byNotiId/{id}")
    public Notification show(@PathVariable String id){
        int notiId = Integer.parseInt(id);
        return notificationRepository.findOne(notiId);
    }
    
    @GetMapping("/notifications/byUserId/{userId}")
    public List<Notification> getNotisByUserId(@PathVariable String userId){
        int theUserId = Integer.parseInt(userId);
        return notificationRepository.findByUserId(theUserId); 
    }
    
    @GetMapping("/notificationCount/byUserId/{userId}")
    public Map<String, Object> getNotiCount(@PathVariable String userId){
        int theUserId = Integer.parseInt(userId);
        int notiCount = notificationRepository.findByUserId(theUserId).size();
        Map<String, Object> map = new HashMap<>();
        map.put("notiCount", notiCount);
        return map;
    }
    
    @PutMapping("/readNotification/byNotiId/{id}")
    public Notification read(@PathVariable String id){
        int notiId = Integer.parseInt(id);
        Notification noti = notificationRepository.findOne(notiId);
        noti.setIsNew(false);
        return notificationRepository.save(noti);
    }
}