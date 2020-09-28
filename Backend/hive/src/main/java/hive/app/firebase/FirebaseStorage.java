package hive.app.firebase;

import java.io.FileInputStream;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;

@Service  
public class FirebaseStorage {  

	@PostConstruct  
	public void initialize() {  
		try { 
			String bucketName = "https://console.firebase.google.com/project/hivephotodb/storage/hivephotodb.appspot.com/files";
			String projectId = "hivephotodb";
			FileInputStream serviceAccount = new FileInputStream("C:\\Users\\julie\\Documents\\workspace-spring-tool-suite-4-4.7.1.RELEASE\\tc_3\\Backend\\hive\\src\\main\\java\\hive\\app\\utils\\serviceAccount.json");
			StorageOptions.newBuilder()
					.setProjectId(projectId)
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();
		} catch (Exception e) {  
			e.printStackTrace();  
		}
	}
	
	
}  