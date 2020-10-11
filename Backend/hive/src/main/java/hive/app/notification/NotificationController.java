
package hive.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class NotificationController {

    @Autowired
    NotificationService notiService;
    
    @GetMapping("/notifications")
    public List<Notification> index(){
        return notiService.findAll();
    }

    @GetMapping("/notifications/byNotiId/{id}")
    public Notification show(@PathVariable String id){
        return notiService.findByNotiId(id);
    }
    
    @GetMapping("/notifications/byUserId/{userId}")
    public List<Notification> getNotisByUserId(@PathVariable String userId){
        return notiService.findByUserId(userId); 
    }
    
    @GetMapping("/notificationCount/byUserId/{userId}")
    public Map<String, Object> getNotiCount(@PathVariable String userId){
    	return notiService.getNotiCount(userId);
    }
    
    @PutMapping("/readNotification/byNotiId/{id}")
    public Notification read(@PathVariable String id){
        return notiService.read(id);
    }
}