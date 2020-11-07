package hive.app.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@Api(description="REST APIs related to the Request entity")
@RestController
public class RequestController {
	
	@Autowired
	RequestService requestService;
	
    @ApiOperation(value="Get all requests")
	@GetMapping("/requests")
	public List<Request> findAll() {
		return requestService.findAll();
	}
	
    @ApiOperation(value="Get all requests for a hive")
	@GetMapping("/requests/byHiveId/{hiveId}")
	public List<Request> findByHiveId(@PathVariable String hiveId){
		return requestService.findByHiveId(hiveId);
	}
	
    @ApiOperation(value="Get all requests made by a user")
	@GetMapping("/requests/byUserId/{userId}")
	public List<Request> findByUserId(@PathVariable String userId){
		return requestService.findByUserId(userId);
	}
	
    @ApiOperation(value="Create a request")
	@PostMapping("/requests")
	public Request create(@RequestBody Map<String, String> body) {
		return requestService.create(body);
	}
	
    @ApiOperation(value="Update a request")
	@PutMapping("/requests")
    public Request update(@RequestBody Map<String, String> body){
        return requestService.update(body);
    }
	
    @ApiOperation(value="Delete a request (set it to inactive)")
	@DeleteMapping("/requests")
	public boolean delete(@RequestBody Map<String, String> body) {
		return requestService.delete(body);
	}
}
