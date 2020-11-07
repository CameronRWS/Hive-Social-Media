
package hive.app.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Notification entity")
@RestController
public class NotificationController {

    @Autowired
    NotificationService notiService;
    
    @ApiOperation(value="Get all notifications")
    @GetMapping("/notifications")
    public List<Notification> index(){
        return notiService.findAll();
    }
    
    @ApiOperation(value="Get a notification by it's notiId")
    @GetMapping("/notifications/byNotiId/{id}")
    public Notification show(@PathVariable String id){
        return notiService.findByNotiId(id);
    }
    
    @ApiOperation(value="Get all notifications by it's userId")
    @GetMapping("/notifications/byUserId/{userId}")
    public List<Notification> getNotisByUserId(@PathVariable String userId){
        return notiService.findByUserId(userId); 
    }
    
    @ApiOperation(value="Get the count of new/unread notifications of a userId")
    @GetMapping("/notificationCount/byUserId/{userId}")
    public Map<String, Object> getNotiCount(@PathVariable String userId){
    	return notiService.getNotiCount(userId);
    }
    
    @ApiOperation(value="Update a notification to be read")
    @PutMapping("/readNotification/byNotiId/{id}")
    public Notification read(@PathVariable String id){
        return notiService.read(id);
    }
}